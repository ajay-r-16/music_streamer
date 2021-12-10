import { Component, OnInit } from '@angular/core';
import { HttpService } from '../service/http.service';
import { UserModel } from '../model/user-model';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { OtpModel } from '../model/otp-model';

declare var $: any;
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  private userModel: UserModel;
  private otpmodel: OtpModel;
  response_text: String = "";
  username: String;
  email: String;
  isload: boolean = false;
  isload1: boolean = false;

  constructor(private http: HttpService, private router: Router) {}

  ngOnInit(): void {
    $("#login_alert").hide();
    $("#otp_alert0").hide();
    $("#otp_alert1").hide();
    $("#otp_alert2").hide();
  }

  alertclose() {
    $("#login_alert").hide();
    $("#otp_alert0").hide();
    $("#otp_alert1").hide();
    $("#otp_alert2").hide();
  }

  EmailForm(emailform: NgForm) {
    this.isload1 = true;
    $("#otp_alert0").hide();
    this.email = emailform.controls['email'].value;
    this.http.getuser(this.email).subscribe((data) => {
      this.isload1 = false;
      if (data == "-1") {
        this.response_text = "Account doesn't exists";
        $("#otp_alert0").show();
      }
      else {
        this.http.Account_id = Number(data)
        $("#exampleModal1").modal('show');
      }
    })
  }

  passOtpForm(passOtp: NgForm) {
    this.isload1 = true;
    $("#otp_alert1").hide();
    this.otpmodel = new OtpModel();
    this.otpmodel.token = passOtp.controls['otp'].value;
    this.otpmodel.id = this.http.Account_id
    this.http.verifyPassCode(this.otpmodel).subscribe((data) => {
      this.isload1 = false
      if (data == 8) {
        this.response_text = "Otp Expired";
        $("#otp_alert1").show();
      }
      else if (data == 11) {
        $("#exampleModal2").modal('show');
      }
      else {
        this.response_text = "Invalid Otp";
        $("#otp_alert1").show();
      }
    })
  }

  passForm(pass: NgForm) {
    this.isload1 = true
    $("#otp_alert2").hide();
    this.userModel = new UserModel();
    this.userModel.password = pass.controls['password1'].value;
    this.userModel.repeatPassword = pass.controls['password2'].value;
    this.userModel.email = String(this.email);
    if (this.userModel.password == this.userModel.repeatPassword) {
      this.http.changePassword(this.userModel).subscribe((data) => {
        this.isload1 = false
        if (data) {
          $("#exampleModal0").modal('hide');
          $("#exampleModal1").modal('hide');
          $("#exampleModal2").modal('hide');
          this.response_text = "Password changed successfully";
          document.getElementById("login_alert").classList.add("alert-success");
          document.getElementById("login_alert").classList.remove("alert-danger");
          $("#login_alert").show();
        }
      })
    }
    else {
      this.isload1 = false
      this.response_text = "Password doesn't match";
      $("#otp_alert2").show();
    }
  }


  UserForm(f: NgForm) {
    this.isload = true;
    this.userModel = new UserModel();
    this.userModel.email = f.controls['inputEmail'].value;
    this.userModel.password = f.controls['inputPassword'].value;
    this.http.login(this.userModel).subscribe((data) => {
      this.isload = false;
      sessionStorage.setItem('username', this.userModel.email);
      let tokenStr = 'Bearer ' + data["token"];
      sessionStorage.setItem('token', tokenStr);
      localStorage.setItem('username', this.userModel.email);
      localStorage.setItem('token', tokenStr);
      this.http.user = this.userModel.email;
      if (data["role"] == 1) {
        this.router.navigate(["/manage"])
      }
      else {
        this.router.navigate(["/home"]);
      }
    }, error => {
      this.isload = false
      this.response_text = "Invalid Email / Password !!";
      document.getElementById("login_alert").classList.remove("alert-success");
      document.getElementById("login_alert").classList.add("alert-danger");
      $("#login_alert").show();
    })
  }
}
