package by.vsu.controller;

/**
 * Хранит куда переходить и какой способ использовать.
 * И может хранить код об ошибке или сообщение, которое надо показать
 * пользователю
 *
 * @author Kovzov Vladislav
 */
public class Forward {
    private String url = null;

    private boolean redirect = true;

    private String msg = null;

    private Integer errorCode = null;

    /**
     *
     * @param url куда надо выполнить переход
     * @param redirect true использовать sendRedirect(), false - getRequestDispatcher().forward()
     */
    public Forward(String url, boolean redirect) {
        this.url = url;
        this.redirect = redirect;
    }

    /**
     *
     * @param url куда надо выполнить переход
     */
    public Forward(String url) {
        this.url = url;
    }

    public Forward(String url, String msg) {
        this.url = url;
        this.msg = msg;
    }


    public Forward(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getUrl() {
        return url;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
