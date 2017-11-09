/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.example.basic_example;

import com.company.sakila.SakilaApplication;
import com.company.sakila.db0.sakila.film.Film;
import com.company.sakila.db0.sakila.film.FilmManager;
import com.company.sakila.db0.sakila.language.Language;
import com.company.sakila.db0.sakila.language.LanguageImpl;
import com.company.sakila.db0.sakila.language.LanguageManager;
import com.speedment.example.basic_example.util.ExampleUtil;
import static com.speedment.example.basic_example.util.ExampleUtil.buildApplication;
import com.speedment.runtime.core.ApplicationBuilder.LogType;
import com.speedment.runtime.core.component.transaction.Isolation;
import com.speedment.runtime.core.component.transaction.TransactionComponent;
import com.speedment.runtime.core.component.transaction.TransactionHandler;
import java.util.List;
import static java.util.stream.Collectors.toList;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg
 */
public class Transaction {

    private final SakilaApplication app;
    private final FilmManager films;
    private final LanguageManager languages;
    private final TransactionComponent transactionComponent;
    private final TransactionHandler txHandler;

    public Transaction() {
        app = buildApplication(
            b -> b.withLogging(LogType.PERSIST),
            b -> b.withLogging(LogType.UPDATE),
            b -> b.withLogging(LogType.REMOVE),
            b -> b.withLogging(LogType.TRANSACTION)
        );
        films = app.getOrThrow(FilmManager.class);
        languages = app.getOrThrow(LanguageManager.class);
        transactionComponent = app.getOrThrow(TransactionComponent.class);
        txHandler = transactionComponent.createTransactionHandler();
    }

    public static void main(String[] args) {
        new Transaction().run();
    }

    private void run() {
        selectTransaction();
        selectTransaction2();
        createLanguagesUncommitted();
        // createLanguagesCommitted();
        deleteCommitted();
        isolationLevel();
    }

    private void selectTransaction() {
        ExampleUtil.log("selectTransaction");
        txHandler.createAndAccept(
            tx -> System.out.println(
                films.stream().filter(Film.LENGTH.greaterThan(75)).count()
                + languages.stream().count()
            )
        );
    }

    private void selectTransaction2() {
        ExampleUtil.log("selectTransaction2");
        long sumCount = txHandler.createAndApply(
            tx -> films.stream().filter(Film.LENGTH.greaterThan(75)).count() + languages.stream().count()
        );
        System.out.println(sumCount);

    }

    private void createLanguagesUncommitted() {
        ExampleUtil.log("createLanguagesUncommitted");
        long noLanguagesInTransaction = txHandler.createAndApply(
            tx -> {
                Stream.of(
                    new LanguageImpl().setName("Italian"),
                    new LanguageImpl().setName("German")
                ).forEach(languages.persister());
                return languages.stream().count();
                // The transaction is implicitly rolled back 
            }
        );
        long noLanguagesAfterTransaction = languages.stream().count();

        System.out.format(
            "no languages in tx %d, no languages after transaction %d %n",
            noLanguagesInTransaction,
            noLanguagesAfterTransaction
        );

    }

    private void createLanguagesCommitted() {
        ExampleUtil.log("createLanguagesCommitted");
        long noLanguagesInTransaction = txHandler.createAndApply(
            tx -> {
                Stream.of(
                    new LanguageImpl().setName("Italian"),
                    new LanguageImpl().setName("German")
                ).forEach(languages.persister());
                tx.commit(); // Make the changes visible outside the tx
                return languages.stream().count();
            }
        );
        long noLanguagesAfterTransaction = languages.stream().count();

        System.out.format(
            "no languages in tx %d, no languages after transaction %d %n",
            noLanguagesInTransaction,
            noLanguagesAfterTransaction
        );

    }

    private void deleteCommitted() {
        ExampleUtil.log("deleteCommitted");
        txHandler.createAndAccept(
            tx -> {
                // Collect to a list before performing actions
                List<Language> toDelete = languages.stream()
                    .filter(Language.LANGUAGE_ID.notEqual((short) 1))
                    .collect(toList());

                // Do the actual actions
                toDelete.forEach(languages.remover());
                
                tx.commit();
            }
        );
        long cnt = languages.stream().count();
        System.out.format("There are %d languages after delete %n", cnt);

    }

    private void isolationLevel() {
        ExampleUtil.log("isolationLevel");
        TransactionHandler txHandler = transactionComponent.createTransactionHandler();
        txHandler.setIsolation(Isolation.READ_COMMITTED);
        long sumCount = txHandler.createAndApply(
            tx -> films.stream().filter(Film.LENGTH.greaterThan(75)).count() + languages.stream().count()
        );
        System.out.println(sumCount);

    }

}
