package com.course.msscorderservice.domain;

public enum BeerOrderEvents {
    VALIDATE_ORDER,
    VALIDATION_PASSED,
    VALIDATION_FAILED,
    ALLOCATION_SUCCESS,
    ALLOCATION_NO_INVENTORY,
    ALLOCATION_FAILED,
    BEERORDER_PICKED_UP
}
