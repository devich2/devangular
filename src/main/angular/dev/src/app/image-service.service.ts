import { Injectable } from '@angular/core';
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ImageServiceService {

  constructor() { }
  public uploadImage(image: File): String{
    const formData = new FormData();

    formData.append('image', image);
return "asd";
   // return this.http.post('/api/v1/image-upload', formData);
  }
}
