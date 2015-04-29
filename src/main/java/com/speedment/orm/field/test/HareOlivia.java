/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.orm.field.test;

/**
 *
 * @author pemi
 */
public class HareOlivia implements Hare {

    @Override
    public String getName() {
        return "olivia";
    }

    @Override
    public String getColor() {
        return "gray";
    }

    @Override
    public int getAge() {
        return 2;
    }

    @Override
    public String toString() {
        return getName() + ", " + getColor() + ", " + getAge();
    }

}
