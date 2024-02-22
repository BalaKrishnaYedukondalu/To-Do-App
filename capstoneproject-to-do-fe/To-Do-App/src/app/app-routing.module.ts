import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ViewnotificationComponent } from './viewnotification/viewnotification.component';
import { AddtodoComponent } from './addtodo/addtodo.component';
import { HeaderComponent } from './header/header.component';
import { ViewtodoComponent } from './viewtodo/viewtodo.component';
import { ViewArchiveTodoComponent } from './view-archive-todo/view-archive-todo.component';
import { ViewCompletedTodoComponent } from './view-completed-todo/view-completed-todo.component';
import { ViewImportantTodoComponent } from './view-important-todo/view-important-todo.component';
import { SearchtodoComponent } from './searchtodo/searchtodo.component';
import { RegisterComponent } from './register/register.component';
import { UpdateTodoComponent } from './update-todo/update-todo.component';
import { TodoByDateComponent } from './todo-by-date/todo-by-date.component';
import { AuthGuard } from './guard/auth.guard';
import { PersonalComponent } from './personal/personal.component';
import { TravelComponent } from './travel/travel.component';
import { CategoryComponent } from './category/category.component';
import { ViewCategoryComponent } from './view-category/view-category.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  {
    path: 'home',
    component: HomeComponent,
    children: [
      { path: 'notification', component: ViewnotificationComponent },

      { path: 'addToDo/:title', component: AddtodoComponent },
      { path: 'updateToDo/:title', component: UpdateTodoComponent },
      { path: 'header', component: HeaderComponent },
      // { path: 'header/:title', component: HeaderComponent },
      { path: 'viewtodo', component: ViewtodoComponent },
      { path: 'viewarchive', component: ViewArchiveTodoComponent },
      { path: 'viewcompleted', component: ViewCompletedTodoComponent },
      { path: 'viewimportant', component: ViewImportantTodoComponent },
      { path: 'todobydate', component: TodoByDateComponent },
      { path: 'search', component: SearchtodoComponent },
      { path: 'Personal', component: PersonalComponent },
      { path: 'Travel', component: TravelComponent },
      { path: 'category', component: CategoryComponent },
      { path: 'viewCategory', component: ViewCategoryComponent },
    ],
    canActivate: [AuthGuard],
  },
  {
    path: 'register',
    component: RegisterComponent,
  },
  { path: 'login', component: LoginComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
