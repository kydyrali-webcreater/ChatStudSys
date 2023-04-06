package org.example.Exceptions.models;

public class BasicException extends RuntimeException {

    private String excepation;
    public BasicException(String excepation) {
        super(excepation);
        this.excepation = excepation;
    }

    @Override
    public String getMessage() {
        return excepation;
    }

}
