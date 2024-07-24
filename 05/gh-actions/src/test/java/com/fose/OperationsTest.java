package com.fose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    private final Operations sut = new Operations();

    @Test
    void add() {
        assertEquals(5, sut.add(2,3));
        assertEquals(0, sut.add(-1,1));
        assertEquals(-5, sut.add(-2,-3));
    }

    @Test
    void subtract() {
        assertEquals(1,sut.subtract(3,2));
        assertEquals(-2,sut.subtract(-1,1));
        assertEquals(5,sut.subtract(2,-3));
    }

    @Test
    void multiply() {
        assertEquals(6, sut.multiply(2, 3));
        assertEquals(-3, sut.multiply(-1, 3));
        assertEquals(0, sut.multiply(5, 0));
    }

    @Test
    void divide() {
        assertEquals(2,sut.divide(8,4));
        assertThrows(ArithmeticException.class, () -> sut.divide(5, 0));
    }
}