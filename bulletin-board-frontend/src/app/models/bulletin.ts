import {User} from './user';
import {Image} from './image';

export interface Bulletin {
  id: number;
  image: Image;
  title: string;
  description: string;
  publishDate: string;
  user: User;
}
