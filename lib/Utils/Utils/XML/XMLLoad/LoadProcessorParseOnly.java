package Utils.XML.XMLLoad;


public abstract class LoadProcessorParseOnly extends LoadProcessor<Object> {

//-------------------------------------------------------------------------
// Simplified Wrapper for XML parsing - without storeing or creating new objects
//-------------------------------------------------------------------------
// This extention does not catalog or save any objects
// It is mearly here to PARSE the XML
// The advantage is that the XML objects loaded are not cataloged, thus saving resorces

  public LoadProcessorParseOnly(String tag) {super(Object.class, tag, false);}

  public Object overlay(Object o, DataWrapper data) {parse(data); return null;}
  public Object create(           DataWrapper data) {parse(data); return null;}

  public abstract void parse(DataWrapper data);

}
