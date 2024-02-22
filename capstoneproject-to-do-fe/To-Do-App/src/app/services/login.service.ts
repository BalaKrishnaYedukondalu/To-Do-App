import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { USER } from '../model/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private httpClient: HttpClient, private router: Router) {}
 
  public setToken(jwtToken: any) {
    localStorage.setItem('jwtToken', jwtToken);
  }
  
  getToken(){
    return localStorage.getItem('jwtToken');
  }

   
  public isLoggedIn() {
    let tokenStr = localStorage.getItem('jwtToken');
    return tokenStr == undefined || tokenStr == '' || tokenStr == null ? false : true;
  }

  
  public logout() {        
    localStorage.removeItem('jwtToken');
    return true;
  }
 
  setUser(user: any) {
    return localStorage.setItem('user', JSON.stringify(user));
  }
  
 
  getUser() {
    let user = localStorage.getItem('user');
    if (user != null) {
      return JSON.parse(user);
    } else {
      this.logout();
      return null;
    }
  }


  public loginUser(user: any): Observable<any> {
    return this.httpClient.post<any>(
      'http://localhost:9005/user/loginUser',
      user
    );
  }

  
  public registerUser(user: USER): Observable<USER> {
    return this.httpClient.post<USER>(
      'http://localhost:9005/toDo/register',
      user
    );
  }
}
