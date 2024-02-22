import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginService } from './login.service';

@Injectable()
export class AuthInterceptorInterceptor implements HttpInterceptor {
  constructor(private loginService: LoginService) {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    let authReq = request;
   
    const jwtToken = this.loginService.getToken();
    // const jwtToken =
    //   'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZGNAZ21haWwuY29tIiwiaWF0IjoxNjk1NjUyNDg1fQ.BmaJY_h0muIYdO58Mqnxi5UTFcNqBLbdoCGDca7nwBQ';
    if (jwtToken != null) {
      authReq = authReq.clone({
        setHeaders: { Authorization: `Bearer${jwtToken}` },
      });
    }

    console.info(authReq);

    return next.handle(authReq);
  }
}
