import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { USER } from '../model/user';
import { ToDo } from '../model/todo';
import { LoginService } from './login.service';

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  //inject HttpClient service in constructor to call backend services
  constructor(private http: HttpClient, private loginService: LoginService) {}

  todoList: ToDo[] = [];

  //created addToDo()
  public addToDo(toDo: any): Observable<any> {
    return this.http.post<any>(`http://localhost:9005/toDo/user/addToDo`, toDo);
  }

  //created getAllToDo()
  public getAllToDo(): Observable<any> {
    // const jwtToken = this.loginService.getToken();
    // let header = new HttpHeaders();
    // if (jwtToken !== null) {
    //   header = new HttpHeaders({
    //     Authorization: `Bearer${jwtToken}`,
    //   });
    // }
    // return this.http.get<any>(`http://localhost:9005/toDo/user/getAllToDo`, {
    //   headers: header,
    // });
    return this.http.get<any>(`http://localhost:9005/toDo/user/getAllToDo`);
  }

  //created updateToDo()
  public updateToDo(toDo: any): Observable<any> {
    return this.http.put<any>(
      `http://localhost:9005/toDo/user/updateToDo`,
      toDo
    );
  }

  //created deleteToDoByTitle()
  public deleteToDoByTitle(title: any): Observable<any> {
    return this.http.delete<any>(
      `http://localhost:9005/toDo/user/deleteToDoByTitle/${title}`
    );
  }

  //created getToDoByTitle()
  public getToDoByTitle(title: any): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/toDo/user/getToDoByTitle/${title}`
    );
  }

  //create getAllListByPriority()
  public getAllListByPriority(priority: any): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/toDo/user/getAllListByPriority/${priority}`
    );
  }

  //create getAllToDoByArchiveStatus()
  public getAllToDoByArchiveStatus(isArchive: any): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/toDo/user/getAllToDoByArchiveStatus/${isArchive}`
    );
  }

  //create updateTaskArchiveStatus()
  public updateTaskArchiveStatus(toDo: ToDo): Observable<Array<ToDo>> {
    return this.http.put<Array<ToDo>>(
      `http://localhost:9005/toDo/user/updateTaskArchiveStatus`,
      toDo
    );
  }

  //create updateToDoStatus()
  public updateToDoStatus(toDo: any): Observable<any> {
    return this.http.put<any>(
      `http://localhost:9005/toDo/user/updateToDoStatus`,
      toDo
    );
  }

  //create getAllToDoByStatus()
  public getAllToDoByStatus(isCompleted: any): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/toDo/user/getAllToDoByStatus/${isCompleted}`
    );
  }

  //create getAllToDoByStatus()
  public getToDoByImportantStatus(isCompleted: any): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/toDo/user/getToDoByImportantStatus/${isCompleted}`
    );
  }

  //created deleteTaskByTitle()
  public deleteTaskByTitle(title: any): Observable<any> {
    return this.http.delete<any>(
      `http://localhost:9005/toDo/user/deleteToDoByTitle/${title}`
    );
  }

  //created getAllNotification()
  public getAllNotification(): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/notificationController/user/getAllNotification`
    );
  }

  public updateNotification(toDo: any): Observable<any> {
    return this.http.put<any>(
      `http://localhost:9005/notificationController/user/updateNotification`,
      toDo
    );
  }

  //created deleteAllToDo()
  public deleteAllToDo(): Observable<any> {
    return this.http.delete<any>(
      `http://localhost:9005/toDo/user/deleteAllToDo`
    );
  }

  public getToDOByCompletedStatus(isCompleted: boolean): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/toDo/user/getAllToDoByStatus/${isCompleted}`
    );
  }

  //create updateTaskArchiveStatus()
  public updateTaskAsCompletedTask(toDo: any): Observable<any> {
    return this.http.put<any>(
      `http://localhost:9005/toDo/user/updateToDoAsCompletedTask`,
      toDo
    );
  }

  //create updateToDoAsImportantTask()
  public updateToDoAsImportantTask(toDo: any): Observable<any> {
    return this.http.put<any>(
      `http://localhost:9005/toDo/user/updateToDoAsImportantTask`,
      toDo
    );
  }

  //getCurrentUser
  public getCurrentUser(): Observable<USER> {
    return this.http.get<USER>(
      `http://localhost:9005/toDo/user/getUserDetails`
    );
  }

  public getToDOByDate(): Observable<any> {
    return this.http.get<any>(`http://localhost:9005/toDo/user/getToDoByDate`);
  }

  //create getAllToDoByStatus()
  public getToDoByPersonalStatus(isCompleted: any): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/toDo/user/getToDoByPersonalStatus/${isCompleted}`
    );
  }

  public updateToDoAsPersonalTask(toDo: any): Observable<any> {
    return this.http.put<any>(
      `http://localhost:9005/toDo/user/updateToDoAsPersonalTask`,
      toDo
    );
  }
  //create getAllToDoByStatus()
  public getToDoByTravelStatus(isCompleted: any): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/toDo/user/getToDoByTravelStatus/${isCompleted}`
    );
  }

  public updateToDoAsTravelTask(toDo: any): Observable<any> {
    return this.http.put<any>(
      `http://localhost:9005/toDo/user/updateToDoAsTravelTask`,
      toDo
    );
  }

  public addCustomCategory(Category: any): Observable<any> {
    return this.http.post<any>(
      'http://localhost:9005/toDo/user/createCategory',
      Category
    );
  }

  public getAllCategory(): Observable<any> {
    return this.http.get<any>('http://localhost:9005/toDo/user/getAllCategory');
  }

  public getAllCatergoryByToDoList(title: string): Observable<any> {
    return this.http.get<any>(
      `http://localhost:9005/toDo/user/getAllCatergoryByToDoList/${title}`
    );
  }

  public addTodoToUserCategory(title: string, toDo: any): Observable<any> {
    return this.http.post<any>(
      `http://localhost:9005/toDo/user/addTodoToUserCategory/${title}`,
      toDo
    );
  }
}
