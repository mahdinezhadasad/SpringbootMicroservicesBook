package bookservice.bookorderservice.domain;

public enum BookOrderEventEnum {
    
    VALIDATE_ORDER, VALIDATION_PASSED, VALIDATION_FAILED,
    ALLOCATION_SUCCESS, ALLOCATION_NO_INVENTORY, ALLOCATION_FAILED,
    BOOKORDER_PICKED_UP,ALLOCATED
}