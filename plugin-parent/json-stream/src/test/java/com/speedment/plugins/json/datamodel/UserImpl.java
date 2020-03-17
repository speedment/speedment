/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
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
package com.speedment.plugins.json.datamodel;

public final class UserImpl implements User {

    private int id;
    private String name;
    private byte aByte;
    private short aShort;
    private long aLong;
    private float aFloat;
    private double aDouble;
    private char aChar;
    private boolean aBoolean;
    private int language;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public User setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public User setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public byte getByte() {
        return aByte;
    }

    @Override
    public User setByte(byte val) {
        aByte = val;
        return this;
    }

    @Override
    public short getShort() {
        return aShort;
    }

    @Override
    public User setShort(short val) {
        aShort = val;
        return this;
    }

    @Override
    public long getLong() {
        return aLong;
    }

    @Override
    public User setLong(long val) {
        aLong = val;
        return this;
    }

    @Override
    public float getFloat() {
        return aFloat;
    }

    @Override
    public User setFloat(float val) {
        aFloat = val;
        return this;
    }

    @Override
    public double getDouble() {
        return aDouble;
    }

    @Override
    public User setDouble(double val) {
        aDouble = val;
        return this;
    }

    @Override
    public char getChar() {
        return aChar;
    }

    @Override
    public User setChar(char val) {
        aChar = val;
        return this;
    }

    @Override
    public boolean getBoolean() {
        return aBoolean;
    }

    @Override
    public User setBoolean(boolean val) {
        aBoolean = val;
        return this;
    }

    @Override
    public int getLanguage() {
        return language;
    }

    @Override
    public User setLanguage(int val) {
        language = val;
        return this;
    }
}
