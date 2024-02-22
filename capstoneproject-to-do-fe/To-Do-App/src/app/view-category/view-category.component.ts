import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TodoService } from '../services/todo.service';
import { Category } from '../model/category';
import { ToDo } from '../model/todo';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-category',
  templateUrl: './view-category.component.html',
  styleUrls: ['./view-category.component.css'],
})
export class ViewCategoryComponent implements OnInit {
  selectedItem = '';
  cards: any[] = [];
  categories: Category[] = [];
  allToDO: ToDo[] = [];
  showMessage: boolean = true;
  panelOpenState = false;
  toast: any;
  constructor(private toDoService: TodoService, private router: Router) {}
  ngOnInit() {
    this.toDoService.getAllCategory().subscribe((data) => console.log(data));
    this.toDoService
      .getAllCategory()
      .subscribe((data) => console.log(this.categories));
    this.toDoService.getAllCategory().subscribe((data) => {
      this.cards = data;
    });
  }
  p: any;

  isOpen = false;
  animateButton() {
    let elem = <HTMLInputElement>document.getElementById('sidebar_sidenav');
    elem.setAttribute('visibility', 'visible');
    if (this.isOpen) this.isOpen = false;
    else this.isOpen = true;
  }

  selected = '';
  @Output()
  sendToContainer1: EventEmitter<any> = new EventEmitter();
  searchinput1() {
    this.sendToContainer1.emit(this.selected);
  }
  home() {
    this.router.navigateByUrl('/home/header');
  }

  important() {
    this.router.navigateByUrl('/home/viewimportant');
  }
  archive() {
    this.router.navigateByUrl('/home/viewarchive');
  }
  complete() {
    this.router.navigateByUrl('/home/viewcompleted');
  }
  Personal() {
    this.router.navigateByUrl('/home/Personal');
  }
  Travel() {
    this.router.navigateByUrl('/home/Travel');
  }

  getAllCatergoryByToDoList() {
    this.toDoService.getAllCategory().subscribe((data) => console.log(data));
  }
  todo: any;
  getToDo(card: any) {
    alert('checking todo ');

    this.todo = card;
    console.log(this.todo) + 'this is my todo dv';
    this.toDoService
      .addTodoToUserCategory(this.selectedItem, card)
      .subscribe((data) => console.log(data));
  }
  deleteTaskById(title: any) {
    this.toDoService.deleteToDoByTitle(title).subscribe({
      next: (data) => {
        this.toast.success({
          detail: 'ToDo Deleted successfully',
          summary: 'DELETED',
          duration: 4000,
        });
        this.allToDO;
      },
      error: (err) =>
        this.toast.error({
          detail: 'Due to the network issue toDo is not deleted',
          summary: 'ERROR',
          duration: 4000,
        }),
    });
  }
}
