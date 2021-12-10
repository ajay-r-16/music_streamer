import { Component, OnInit, ViewChild } from '@angular/core';
import { HttpService } from '../service/http.service';
import { UserModel } from '../model/user-model';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { OtpModel } from '../model/otp-model';

declare var $: any;

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {
  private userModel = new UserModel();
  private otpmodel = new OtpModel();
  showalert: boolean = false;
  response_text: String = " ";
  acc_id: number;
  time: number;
  final_time: number;
  min: number;
  sec: number;
  interval: any;
  isload: boolean = false;
  isload1: boolean = false;

  constructor(private http: HttpService, private router: Router) { }

  ngOnInit(): void {
    $("#signup_alert").hide();
    $("#otp_alert").hide();
  }

  UserForm(f: NgForm) {
    this.isload = true;
    this.userModel.username = f.controls['name'].value;
    this.userModel.email = f.controls['inputEmail'].value;
    this.userModel.password = f.controls['inputPassword1'].value;
    this.userModel.repeatPassword = f.controls['inputPassword2'].value;
    if (this.userModel.password == this.userModel.repeatPassword) {
      $("#signup_alert").hide();
      this.http.register(this.userModel).subscribe((data) => {
        if (data == "-1") {
          this.response_text = "Email already exists !!";
          $("#signup_alert").show();
        }
        else {
          this.http.Account_id = Number(data);
          this.time = new Date().getTime() + (3 * 60 * 1000);
          $("#resend").hide();
          $("#exampleModal1").modal('show');
          this.repeat();
        }
        this.isload = false;
      })
    }
    else {
      this.response_text = "Password doesn't match";
      $("#signup_alert").show();
    }
  }

  repeat() {
    this.final_time = this.time - (new Date().getTime());
    this.min = Math.floor(Math.floor(this.final_time / 1000) / 60)
    this.sec = Math.floor(Math.floor(this.final_time / 1000) % 60)
    $("#resend").hide();
    $("#resend_time").show();
    this.interval = setInterval(() => {
      this.final_time = this.time - (new Date().getTime());
      if (this.final_time <= 0) {
        $("#resend").show();
        $("#resend_time").hide();
        clearInterval(this.interval);
      }
      this.min = Math.floor(Math.floor(this.final_time / 1000) / 60)
      this.sec = Math.floor(Math.floor(this.final_time / 1000) % 60)
    }, 1000)
  }

  resendOtp() {
    $("#resend").hide();
    this.isload1 = true;
    this.http.resendOtp(this.userModel).subscribe((data: String) => {
      this.isload1 = false
      this.acc_id = Number(data);
      this.time = new Date().getTime() + (3 * 60 * 1000);
      $("#resend").hide();
      this.repeat();
    })
  }

  alertclose() {
    $("#signup_alert").hide();
    $("#otp_alert").hide();
  }

  OtpForm(form: NgForm) {
    this.isload1 = true
    this.otpmodel.token = form.controls['otp'].value;
    this.otpmodel.id = this.http.Account_id;
    this.http.verify(this.otpmodel).subscribe((data) => {
      this.isload1 = false
      if (data == 8) {
        this.response_text = "Otp Expired";
        $("#otp_alert").show();
      }
      else if (data == 11) {
        $("#exampleModal1").modal('hide');
        this.router.navigate(["/"]);
      }
      else {
        this.response_text = "Invalid Otp";
        $("#otp_alert").show();
      }
    })
  }
}
