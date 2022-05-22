package dao.factory;

import dao.impl.ServiceDapImpl;

public class ServiceFactory {
  public static ServiceDapImpl newInstance(){
    return new ServiceDapImpl();
  }
}
