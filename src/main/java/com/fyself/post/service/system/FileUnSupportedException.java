package com.fyself.post.service.system;

import com.fyself.seedwork.FySelfException;

import static com.fyself.seedwork.i18n.MessageContextHolder.msg;

/**
 * Thrown to indicate that the sign up process failed by file unsupported.
 *
 * @author jmmarin
 * @since 0.1.0
 */
public class FileUnSupportedException extends FySelfException {
    private static final long serialVersionUID = 4441055929584736192L;

    public FileUnSupportedException(String code) {
        this(code, null);
    }

    public FileUnSupportedException(String code, Throwable cause) {
        super(msg(code), cause);
    }

    /**
     * Create a new {@link FileUnSupportedException} with the message obtained from the given code.
     *
     * @param code The code to get the message within the i18n context.
     * @return An {@link FileUnSupportedException} with the message linked to the code specified.
     */
    static public FileUnSupportedException fileUnSupportedException(String code) {
        return new FileUnSupportedException(code);
    }

    /**
     * Create a new {@link FileUnSupportedException} with the message obtained from the given code.
     *
     * @param code  The code to get the message within the i18n context.
     * @param cause The exception occurred.
     * @return An {@link FileUnSupportedException} with the message linked to the code specified.
     */
    static public FileUnSupportedException fileUnSupportedException(String code, Throwable cause) {
        return new FileUnSupportedException(code, cause);
    }
}
