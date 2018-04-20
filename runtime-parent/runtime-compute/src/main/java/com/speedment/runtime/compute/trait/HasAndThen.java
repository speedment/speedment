package com.speedment.runtime.compute.trait;

import com.speedment.runtime.compute.*;

import java.util.function.Function;

/**
 * @author Emil Forslund
 * @since  3.1.0
 */
public interface HasAndThen<TUPLE, R> extends Function<TUPLE, R> {


    /*
    Tuple2<Employee, Salary> tuple;


    tuple.getter0().andThen(Employee.EMP_NO) // ToLong<Tuple2<Employee, Salary>>
    tuple.getter1().andThen(Employee.SALARY) // ToDouble<Tuple2<Employee, Salary>>



    tuple.getter0() // TupleFunction<Tuple2<Employee, Salary>, Employee>
    tuple.getter1() // TupleFunction<Tuple2<Employee, Salary>, Salary>

    tuple.getter0().andThen(Employee.EMP_NO) // ToDouble<Tuple2<Employee, Salary>>



     */


    ToDouble<TUPLE> andThen(ToDouble<R> after);

    ToFloat<TUPLE> andThen(ToFloat<R> after);

    ToLong<TUPLE> andThen(ToLong<R> after);

    ToInt<TUPLE> andThen(ToInt<R> after);

    ToShort<TUPLE> andThen(ToShort<R> after);

    ToByte<TUPLE> andThen(ToByte<R> after);

    ToBoolean<TUPLE> andThen(ToBoolean<R> after);

    ToChar<TUPLE> andThen(ToChar<R> after);

    <E extends Enum<E>> ToEnum<TUPLE, E> andThen(ToEnum<R, E> after);

    ToBigDecimal<TUPLE> andThen(ToBigDecimal<R> after);

    ToString<TUPLE> andThen(ToString<R> after);

    ToDoubleNullable<TUPLE> andThen(ToDoubleNullable<R> after);

    ToFloatNullable<TUPLE> andThen(ToFloatNullable<R> after);

    ToLongNullable<TUPLE> andThen(ToLongNullable<R> after);

    ToIntNullable<TUPLE> andThen(ToIntNullable<R> after);

    ToShortNullable<TUPLE> andThen(ToShortNullable<R> after);

    ToByteNullable<TUPLE> andThen(ToByteNullable<R> after);

    ToBooleanNullable<TUPLE> andThen(ToBooleanNullable<R> after);

    ToCharNullable<TUPLE> andThen(ToCharNullable<R> after);

    <E extends Enum<E>> ToEnumNullable<TUPLE, E> andThen(ToEnumNullable<R, E> after);

    ToBigDecimalNullable<TUPLE> andThen(ToBigDecimalNullable<R> after);

    ToStringNullable<TUPLE> andThen(ToStringNullable<R> after);

}
