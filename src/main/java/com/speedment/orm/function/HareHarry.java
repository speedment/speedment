/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.orm.function;

import com.speedment.orm.field.test.Hare;

/**
 *
 * @author pemi
 */
@Deprecated
public class HareHarry implements Hare {

    @Override
    public String getName() {
        return "harry";
    }

    @Override
    public String getColor() {
        return "gray";
    }

    @Override
    public int getAge() {
        return 3;
    }

    @Override
    public String toString() {
        return getName() + ", " + getColor() + ", " + getAge();
    }

}
