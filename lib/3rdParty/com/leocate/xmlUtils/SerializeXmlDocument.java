package com.leocate.xmlUtils;
 
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
 
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;
 
public class SerializeXmlDocument{
	
	private Document doc;
	private File xmlFile;
	private String indent;
	private String lineSeperator;
	
	public SerializeXmlDocument(){
		this.indent=" ";
		this.lineSeperator = "\n";
	}
	
	public void serializeXmlDoc(Document doc,OutputStream out)throws IOException{
		Writer writer = new OutputStreamWriter(out);
		serializeXmlDoc(doc,writer);
	}
	
	public void serializeXmlDoc(Document doc,File xmlFile)throws IOException{
		Writer writer = new FileWriter(xmlFile);
		serializeXmlDoc(doc,writer);
	}
	
	public void serializeXmlDoc(Document doc,Writer writer)throws IOException{
		serializeNode(doc,writer,"");
		writer.flush();
	}
	
	public void serializeNode(Node node,Writer writer,String indentLevel)throws IOException{
		switch(node.getNodeType()){
			case Node.DOCUMENT_NODE:
				writer.write("<?xml version=\"1.0\"?>\n");
				
				//recurse on each child
				NodeList nodes = node.getChildNodes();
				if(nodes!=null){
					for(int i=0;i<nodes.getLength();i++){
						serializeNode(nodes.item(i),writer,"");
					}
				}
				break;
			case Node.ELEMENT_NODE:
				String name = node.getNodeName();
				writer.write("<"+name);
				NamedNodeMap attributes = node.getAttributes();
				for(int i=0;i<attributes.getLength();i++){
					Node current = attributes.item(i);
					writer.write(" "+current.getNodeName()+"=\""+current.getNodeValue()+"\"");
				}
				writer.write(">\n");
				//recurse on each child
				NodeList children = node.getChildNodes();
			
				for(int i=0;i<children.getLength();i++){
					serializeNode(children.item(i),writer,indentLevel+indent);
				}
				
				writer.write("</"+name+">");
				
				break;
			case Node.TEXT_NODE:
				if(node.getNodeValue()!=null){
					writer.write(node.getNodeValue());
				}
				break;
		}
	}
	
}

