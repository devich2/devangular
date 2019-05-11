import {Component, HostListener, Inject, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import * as Stomp from '@stomp/stompjs';
import * as SockJS from 'sockjs-client';
import {HttpClient, HttpHeaders,} from '@angular/common/http';
import {CompatClient} from '@stomp/stompjs';
import {Message} from "./chat.model";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.css'],
})
export class ChatComponent implements OnInit, OnDestroy {

  private stompClient: CompatClient = null;
  messages: string[] = [];
  chatName: string = "";
  joinLink: string = "";
  error: string = "";
  token: string = "";

  constructor(private route: ActivatedRoute,
              private http: HttpClient) {
    let that = this;
    window.onbeforeunload = function(e) {
     that.disconnect();
    };
  }

  ngOnInit(): void {
    this.getChatName();
    this.getApproveToUseChat();
  }

  ngOnDestroy(): void {
    this.disconnect();
  }

  connect(): void {
    console.log("Trying to connect..");
    const socket = new SockJS('http://localhost:8080/connectSocket');
    this.stompClient = Stomp.Stomp.over(socket);

    console.log(`/topic/chat/${this.chatName}`);
    const _this = this;
    this.stompClient.connect({}, function (frame) {
      console.log('Connected: ' + frame);
      _this.stompClient.subscribe(`/topic/${_this.chatName}`, function (message) {
        _this.showMessage(JSON.parse(message.body));
      });
    });

  }

  disconnect(): void {
    if (this.stompClient != null) {
      this.stompClient.disconnect();
    }
    this.http.get(`/api/chat/${this.chatName}/disconnect`).subscribe();
  }

  sendMessage(message: string): void {
    this.http.get(`/api/chat/${this.chatName}/sendmessage?content=${message}`, {withCredentials: true})
      .subscribe(data => {
        this.sendBySingature(message);
        console.log(data);
      }, console.log);
  }

  sendBySingature(message: string): void {
    this.stompClient.send(
      `/app/socket/chat/${this.chatName}/sendmessage`,
      {},
      JSON.stringify({'content': message, 'token': this.token})
    );
  }

  showMessage(message: Message): void {
    console.log(message.ok);

    console.log(message["result"]);
    for (let res of message.result) {
      console.log("nice");
      this.messages = [...this.messages, ...res.content];
      console.log(res.content);
    }

    //this.messages.push(message);
  }

  getChatName() {
    this.chatName = this.route.snapshot.paramMap.get('name');
    this.joinLink = "/api/chat/" + this.chatName + "/joinUser";
    console.log("chat : " + this.chatName)
  }

  getApproveToUseChat() {
    const headers = new HttpHeaders();
    this.http.get("/api/chat/" + this.chatName, {withCredentials: true})
      .subscribe(data => {
        const parsed = data;
        if (!parsed["ok"] && data["result"][0].content == "1") {
          this.joinChat();
        } else if (parsed["ok"]) {
          this.token = data["result"][0].type;
          console.log("Approved");
          this.connect();
        }
      }, error => {
        console.log(error)

      });
  }

  joinChat() {
    console.log("joined")
    this.http.get("/api/chat/" + this.chatName + "/joinUser").subscribe(data => {
      if (data["ok"]) {
        this.token = data["result"][0].content;
        console.log("Our token : " + this.token);
        this.connect();
      }
    }, error => {
      console.log(error)

    });
  }

}
