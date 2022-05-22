package dao.factory;

import dao.implement.ServiceDapImpl;

public class ServiceFactory {
  public static ServiceDapImpl newInstance(){
    return new ServiceDapImpl();
  }
}
