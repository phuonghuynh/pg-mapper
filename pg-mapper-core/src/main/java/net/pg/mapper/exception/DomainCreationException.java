package net.pg.mapper.exception;

public class DomainCreationException extends RuntimeException {

   private static final long serialVersionUID = 1L;

   public DomainCreationException() {
      super();
   }

   public DomainCreationException(String message, Throwable cause) {
      super(message, cause);
   }

   public DomainCreationException(String message) {
      super(message);
   }

   public DomainCreationException(Throwable cause) {
      super(cause);
   }

}
