import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { TodoService } from '../services/todo.service';
import { LoginService } from '../services/login.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ToDo } from '../model/todo';
import { NgToastService } from 'ng-angular-popup';
import { MatDialog } from '@angular/material/dialog';
import { CategoryComponent } from '../category/category.component';
import { Category } from '../model/category';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-viewtodo',
  templateUrl: './viewtodo.component.html',
  styleUrls: ['./viewtodo.component.css'],
})
export class ViewtodoComponent implements OnInit {
  p: any;
  showMessage: boolean = false;
  panelOpenState = false;
  allToDO: ToDo[] = [];
  name: any;
  showFiller = false;
  categories: Category[] = [];
  userDetails: any;
  cards: any;
  isOpen = false;
  selected = '';
  selectedItem: any;
  constructor(
    private toDoService: TodoService,
    private fb: FormBuilder,
    private userService: LoginService,
    private router: Router,
    private ar: ActivatedRoute,
    private toast: NgToastService,
    public dialog: MatDialog
  ) {
    this.getUser();
  }

  ngOnInit(): void {
    this.toDoService
      .getCurrentUser()
      .subscribe((data) => console.log(data.firstName));

    this.getAllToDo();
    this.allToDO;
    this.toDoService
      .getAllCategory()
      .subscribe((data) => console.log(this.categories));
    this.toDoService.getAllCategory().subscribe((data) => {
      this.cards = data;
    });
  }

  taskForm: any = {
    title: '',
    description: '',
    priority: '',
    dueDate: '',
    isCompleted: [false],
    isArchive: [false],
    updatedTask: [''],
    createdDateTime: [''],
  };

  reset() {
    this.userDetails.categories = null;
  }

  getUser() {
    this.toDoService.getCurrentUser().subscribe((data) => {
      this.userDetails = data;
      console.log(this.userDetails.category + 'user Name');
    });
  }

  animateButton() {
    let elem = <HTMLInputElement>document.getElementById('sidebar_sidenav');
    elem.setAttribute('visibility', 'visible');
    if (this.isOpen) this.isOpen = false;
    else this.isOpen = true;
  }

  @Output()
  sendToContainer1: EventEmitter<any> = new EventEmitter();
  searchinput1() {
    this.sendToContainer1.emit(this.selected);
  }

  openDialog() {
    this.dialog.open(CategoryComponent);
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

  category() {
    this.router.navigateByUrl('/home/viewCategory');
  }

  deleteTaskById(title: any) {
    this.toDoService.deleteToDoByTitle(title).subscribe({
      next: (data) => {
        location.reload();
        this.toast.success({
          detail: 'ToDo Deleted successfully',
          summary: 'DELETED',
          duration: 4000,
        });
      },
      error: (err) =>
        this.toast.error({
          detail: 'Due to the network issue toDo is not deleted',
          summary: 'ERROR',
          duration: 4000,
        }),
    });
  }

  addToDo() {
    this.router.navigateByUrl('home/addToDo/');
  }

  search(title: string) {
    console.log(title);
    if (title === '' || !title) {
      this.getAllToDo();
    } else {
      this.allToDO = this.allToDO.filter(
        (data) =>
          data.title?.toLocaleLowerCase().includes(title) ||
          data.description?.toLocaleLowerCase().includes(title)
      );
      console.log('Filtered Data : ' + this.allToDO);
    }
  }

  filter(priority: string) {
    if (priority == '') {
      this.getAllToDo();
    } else {
      this.toDoService.getAllListByPriority(priority).subscribe((data) => {
        this.allToDO = data.filter(
          (a: { completed: boolean }) => a.completed == false
        );
        this.allToDO.forEach((x) => (x.isEditable = false));
      });
    }
  }

  completionStatus(isCompleted: boolean) {
    this.toDoService.getToDOByCompletedStatus(isCompleted).subscribe((data) => {
      this.allToDO = data;
    });
  }

  getAllToDo() {
    if (!(this.allToDO == null || this.allToDO.length == 0)) {
      this.showMessage = false;
    } else {
      this.showMessage = true;
    }
    this.toDoService.getAllToDo().subscribe((data) => {
      this.allToDO = data.filter(
        (a: { archive: boolean; completed: boolean }) =>
          a.completed == false && a.archive == false
      );
      if (!(this.allToDO == null || this.allToDO.length == 0)) {
        this.showMessage = false;
      } else {
        this.showMessage = true;
      }

      this.toDoService.todoList = this.allToDO;
      this.toDoService.todoList.forEach((x) => (x.isEditable = false));

      //alert(this.toDoService.todoList.length)
      console.log('get all todolist');
      console.table(this.allToDO);
    });
  }

  editTaskAsCompletedTask(card: any) {
    card.isEditable = true;
  }

  saveTaskAsCompletedTask(card: any) {
    card.isEditable = false;
    console.log(card.title);
    if (card.title != '') {
      this.toDoService.getToDoByTitle(card.title).subscribe((data) => {
        this.taskForm = {
          title: data.title,
          description: card.description,
          dueDate: card.dueDate,
          priority: card.priority,
          isCompleted: data.completed,
          isArchive: data.archive,
          updatedTask: data.updatedTask,
          createdDateTime: data.createdDateTime,
        };

        this.toDoService.updateToDo(this.taskForm).subscribe({
          next: (data1) => {
            this.toast.success({
              detail: 'ToDo update successfully',
              summary: 'UPDATE',
              duration: 4000,
            });
          },
          error: (err) => {
            this.toast.error({
              detail: 'Due to the network issue',
              summary: 'ERROR',
              duration: 4000,
            });
          },
        });
      });
    }
  }
  cancelTaskAsCompletedTask() {
    this.getAllToDo();
  }

  updateTaskArchiveStatus(card: any) {
    this.toDoService.updateTaskArchiveStatus(card).subscribe({
      next: (data) => {
        location.reload();
        this.toast.success({
          detail: 'Archive ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  updateTaskAsCompletedTask(card: any) {
    this.toDoService.updateTaskAsCompletedTask(card).subscribe({
      next: () => {
        this.getAllToDo();
        // location.reload()
        this.toast.success({
          detail: 'Completed ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  updateToDoAsImportantTask(card: any) {
    this.toDoService.updateToDoAsImportantTask(card).subscribe({
      next: () => {
        this.toast.success({
          detail: 'Important ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  updateToDoAsPersonalTask(card: any) {
    this.toDoService.updateToDoAsPersonalTask(card).subscribe({
      next: () => {
        this.toast.success({
          detail: 'personal ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  updateToDoAsTravelTask(card: any) {
    this.toDoService.updateToDoAsTravelTask(card).subscribe({
      next: () => {
        this.toast.success({
          detail: 'travel ToDo Is Added',
          summary: 'Added',
          duration: 4000,
        });
      },
      error: (err) => {
        this.toast.success({
          detail: 'due to the network issues',
          summary: 'Error',
          duration: 4000,
        });
      },
    });
  }

  taskForm2: any = this.fb.group({
    priority: [''],
  });
  get priority() {
    return this.taskForm2.get('priority');
  }

  onSubmit() {
    console.table(this.taskForm2.value);
  }

  items = [
    { label: 'Option 1', value: 'option1' },
    { label: 'Option 2', value: 'option2' },
    { label: 'Option 3', value: 'option3' },
  ];

  onDropdownChange() {
    console.log('Selected Item:', this.selectedItem);

    this.reset();
  }

  getAllCatergoryByToDoList() {
    this.toDoService.getAllCategory().subscribe((data) => console.log(data));
  }
  
  todo: any;
  getToDo(card: any) {
    alert('toDo Added');

    this.todo = card;
    console.log(this.todo) + 'this is my todo dv';
    this.toDoService
      .addTodoToUserCategory(this.selectedItem, card)
      .subscribe((data) => console.log(data));
    location.reload();
  }
}
