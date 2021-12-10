import { BrowserModule } from '@angular/platform-browser';
import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CommonComponent } from './common/common.component';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { SignupComponent } from './signup/signup.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { NavbarComponent } from './navbar/navbar.component';
import { ManageFilesComponent } from './manage-files/manage-files.component';
import { FooterComponent } from './footer/footer.component';
import { HttpService } from './service/http.service';
import { MusicService } from './service/music.service';

@NgModule({
  declarations: [
    AppComponent,
    CommonComponent,
    NavbarComponent,
    HomeComponent,
    LoginComponent,
    ManageFilesComponent,
    SignupComponent,
    FooterComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    AppRoutingModule,
    RouterModule.forRoot([
      {
        path: '',
        component: LoginComponent
      },
      {
        path: 'signup',
        component: SignupComponent
      },
      {
        path: 'home',
        component: HomeComponent
      },
      {
        path: 'common/:term',
        component: CommonComponent
      },
      {
        path: 'common/:term/:term2',
        component: CommonComponent
      },
      {
        path: 'manage',
        component: ManageFilesComponent
      }
    ])
  ],
  providers: [HttpService, MusicService],
  bootstrap: [AppComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})

export class AppModule { }
