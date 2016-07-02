package net.pg.mapper.exception;

public class InvalidFormatMapperException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public InvalidFormatMapperException() {
      super();
   }

   public InvalidFormatMapperException(String message, Throwable cause) {
      super(message, cause);
   }

   public InvalidFormatMapperException(String message) {
      super(message);
   }

   public InvalidFormatMapperException(Throwable cause) {
      super(cause);
   }

}
