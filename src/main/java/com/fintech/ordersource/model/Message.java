package com.fintech.ordersource.model;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;


@Component
public class Message<T> {

  private String type;
  private String id = UUID.randomUUID().toString(); // unique id of this message
  private String source;
  //@JsonFormat(shape = JsonFormat.Shape.STRING) // ISO-8601 compliant format  
 // private Instant time = Instant.now();
  private T data;
  private String datacontenttype="application/json";
  private String specversion="1.0";
  
  // Extension attributes
  private String traceid = UUID.randomUUID().toString(); // trace id, default: new unique
  private String correlationId = UUID.randomUUID().toString(); // id which can be used for correlation later if required
  private String group = "order-topic";
  
  public Message() {    
  }
  
  public Message(String type, T payload) {
    this.type = type;
    this.data = payload;
  }
  
public Message(String type, String traceid, T payload) {
    this.type = type;
    this.traceid = traceid;
    this.data = payload;
  }

  @Override
  public String toString() {
    return "Message [type=" + type + ", id=" + id + ", time=" + "time "+ ", data=" + data + ", correlationid=" + correlationId + ", traceid=" + traceid + "]";
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

//  public Instant getTime() {
//    return time;
//  }
//
//  public void setTime(Instant time) {
//    this.time = time;
//  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getTraceid() {
    return traceid;
  }

  public void setTraceid(String traceid) {
    this.traceid = traceid;
  }

  public String getCorrelationId() {
    return correlationId;
  }

  public void setCorrelationId(String correlationid) {
    this.correlationId = correlationid;
  }

  public String getSource() {
    return source;
  }

  public String getDatacontenttype() {
    return datacontenttype;
  }

  public String getSpecversion() {
    return specversion;
  }

  public String getGroup() {
    return group;
  }

}
