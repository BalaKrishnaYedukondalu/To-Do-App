import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './home/home.component';
import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSelectModule } from '@angular/material/select';
import { MatRadioModule } from '@angular/material/radio';
import { MatCardModule } from '@angular/material/card';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatNativeDateModule } from '@angular/material/core';
import { MatMenuModule } from '@angular/material/menu';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatBadgeModule } from '@angular/material/badge';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatDividerModule } from '@angular/material/divider';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatPaginatorModule } from '@angular/material/paginator';
import { AddtodoComponent } from './addtodo/addtodo.component';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { HeaderComponent } from './header/header.component';
import { ViewtodoComponent } from './viewtodo/viewtodo.component';
import { SearchtodoComponent } from './searchtodo/searchtodo.component';
import { ViewnotificationComponent } from './viewnotification/viewnotification.component';
import { ViewCompletedTodoComponent } from './view-completed-todo/view-completed-todo.component';
import { ViewImportantTodoComponent } from './view-important-todo/view-important-todo.component';
import { ViewArchiveTodoComponent } from './view-archive-todo/view-archive-todo.component';
import { NgToastModule } from 'ng-angular-popup';
import { TodoByDateComponent } from './todo-by-date/todo-by-date.component';
import { UpdateTodoComponent } from './update-todo/update-todo.component';
import { FooterComponent } from './footer/footer.component';
import { NgxPaginationModule } from 'ngx-pagination';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { AuthInterceptorInterceptor } from './services/auth-interceptor.interceptor';
import { LayoutModule } from '@angular/cdk/layout';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatListModule } from '@angular/material/list';
import { PersonalComponent } from './personal/personal.component';
import { TravelComponent } from './travel/travel.component';
import { CategoryComponent } from './category/category.component';
import { ViewCategoryComponent } from './view-category/view-category.component';
 
@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    RegisterComponent,
    LoginComponent,
    AddtodoComponent,
    HeaderComponent,
    ViewtodoComponent,
    SearchtodoComponent,
    ViewnotificationComponent,
    ViewCompletedTodoComponent,
    ViewImportantTodoComponent,
    ViewArchiveTodoComponent,
    TodoByDateComponent,
    UpdateTodoComponent,
    FooterComponent,
    PersonalComponent,
    TravelComponent,
    CategoryComponent,
    ViewCategoryComponent,
     
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatRadioModule,
    MatCardModule,
    MatNativeDateModule,
    MatMenuModule,
    MatSnackBarModule,
    MatTooltipModule,
    MatBadgeModule,
    MatDialogModule,
    MatIconModule,
    MatToolbarModule,
    MatDatepickerModule,
    MatExpansionModule,
    NgToastModule,
    MatDividerModule,
    MatPaginatorModule,
    MatIconModule,
    NgxPaginationModule,
    HttpClientModule,
    LayoutModule,
    MatSidenavModule,
    MatListModule,
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptorInterceptor,
      multi: true,
    },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
