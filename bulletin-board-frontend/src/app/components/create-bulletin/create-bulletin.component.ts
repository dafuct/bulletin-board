import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BulletinCreateForm} from '../../models/bulletin-create-form';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-create-bulletin',
  templateUrl: './create-bulletin.component.html',
  styleUrls: ['./create-bulletin.component.css']
})
export class CreateBulletinComponent implements OnInit {
  bulletinsForm: FormGroup;
  imageUrl: string = null;
  imageExtension: string = null;
  fieldErrorStyle = '1px #ff0000 solid';
  fieldNotEmptyMessage = 'This field is required';
  binaryValue: string = null;

  @Output()
  saveBulletin: EventEmitter<any> = new EventEmitter<any>();

  constructor(private formBuilder: FormBuilder) {
    this.bulletinsForm = formBuilder.group({
      title: ['', [Validators.required, Validators.maxLength(50)]],
      description: ['', [Validators.required, Validators.maxLength(500)]]
    });
  }

  ngOnInit(): void {
  }

  getFormField(fieldName: string) {
    return this.bulletinsForm.get(fieldName);
  }

  isFormFieldValid(fieldName: string) {
    return this.bulletinsForm.controls[fieldName]?.untouched || this.bulletinsForm.controls[fieldName]?.valid;
  }

  onImageChange(event) {
    const imageInput = event.target;
    if (imageInput.files.length) {
      const imageFile = imageInput.files[0];
      const maxImageSize = environment.maxImageSize;
      if (imageFile.size > maxImageSize) {
        alert('Selected image is too big');
      } else if (environment.imageExtensions.indexOf(imageFile.type) === -1) {
        alert('Selected file type is not supported');
      } else {
        this.imageExtension = imageFile.name.substring(imageFile.name.lastIndexOf('.'), imageFile.name.length);
        const reader = new FileReader();
        reader.readAsDataURL(imageFile);
        reader.onload = () => {
          this.imageUrl = reader.result as string;
          reader.readAsBinaryString(imageFile);
          reader.onload = () => {
            this.binaryValue = reader.result as string;
          };
        };
      }
    }
  }

  resetImage() {
    this.imageUrl = null;
  }

  saveBulletinClick() {
    if (this.bulletinsForm.valid) {
      const bulletinData: BulletinCreateForm = this.bulletinsForm.value;
      if (this.imageUrl) {
        this.imageUrl = this.imageUrl.replace(environment.imageUrl[this.imageExtension.substring(1)], '');
        bulletinData.imageBase64 = this.imageUrl;
        bulletinData.imageExtension = this.imageExtension;
      }
      this.saveBulletin.emit(bulletinData);
      this.bulletinsForm.reset();
      this.imageUrl = null;
    } else {
      this.saveBulletin.emit({error: 'Entered data is invalid'});
    }
  }
}
