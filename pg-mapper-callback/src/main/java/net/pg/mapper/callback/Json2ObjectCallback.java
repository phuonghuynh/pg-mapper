package net.pg.mapper.callback;

import net.pg.mapper.callback.DomainMapperCallback;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONValue;

public class Json2ObjectCallback implements DomainMapperCallback {

   private static final Log LOG = LogFactory.getLog(Json2ObjectCallback.class);

   public Object createMe(Object me) {
      try {
         return JSONValue.parse(me.toString());
      }
      catch (Exception e) {
         LOG.error("Invalid Json string", e);
      }
      return null;
   }
}
