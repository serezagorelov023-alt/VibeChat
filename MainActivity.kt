package com.vibe.chat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class Chat(val name:String,val last:String,val time:String,val online:Boolean=false)

class MainActivity: ComponentActivity(){ override fun onCreate(savedInstanceState:Bundle?){super.onCreate(savedInstanceState);setContent{VibeChatApp()}} }

@Composable fun VibeChatApp(){
    var tab by remember { mutableStateOf(0) }
    var selected by remember { mutableStateOf<Chat?>(null) }
    if(selected!=null){ ChatScreen(selected!!){selected=null}; return }
    Scaffold(bottomBar={ NavigationBar { listOf("Чаты","Контакты","Профиль").forEachIndexed{ i,t -> NavigationBarItem(selected=tab==i,onClick={tab=i},icon={Icon(if(i==0) Icons.Default.Chat else if(i==1) Icons.Default.People else Icons.Default.Person,t)},label={Text(t)}) } } }) { p ->
        Column(Modifier.fillMaxSize().padding(p).padding(horizontal=16.dp)){
            Row(Modifier.fillMaxWidth().padding(top=18.dp,bottom=14.dp),verticalAlignment=Alignment.CenterVertically){ Text("Vibe Chat",fontSize=30.sp,fontWeight=FontWeight.Bold,modifier=Modifier.weight(1f)); IconButton(onClick={}){Icon(Icons.Default.Search,"Поиск")}; IconButton(onClick={}){Icon(Icons.Default.Edit,"Новый чат")} }
            when(tab){0->Chats{selected=it};1->Contacts();2->Profile()}
        }
    }
}

@Composable fun Chats(onOpen:(Chat)->Unit){ val chats=listOf(Chat("Алекс","Привет! Как дела?","12:48",true),Chat("Мария","Отправила фото","11:32"),Chat("Vibe Group","Сергей: Всем привет!","10:05",true),Chat("Иван","Увидимся вечером","Вчера")); LazyColumn{items(chats){c-> Row(Modifier.fillMaxWidth().clickable{onOpen(c)}.padding(vertical=10.dp),verticalAlignment=Alignment.CenterVertically){Avatar(c.name,c.online);Column(Modifier.weight(1f).padding(start=12.dp)){Text(c.name,fontWeight=FontWeight.SemiBold,fontSize=17.sp);Text(c.last,maxLines=1,color=Color.Gray)}Text(c.time,color=Color.Gray,fontSize=12.sp)}}} }

@Composable fun Contacts(){Column(Modifier.fillMaxWidth()){Text("Контакты",fontSize=22.sp,fontWeight=FontWeight.Bold);Spacer(Modifier.height(12.dp));Text("Найти друзей по имени или номеру",color=Color.Gray);Spacer(Modifier.height(24.dp));Button(onClick={}){Icon(Icons.Default.PersonAdd,null);Spacer(Modifier.width(8.dp));Text("Добавить контакт")}}}
@Composable fun Profile(){Column(horizontalAlignment=Alignment.CenterHorizontally,modifier=Modifier.fillMaxWidth()){Spacer(Modifier.height(20.dp));Box(Modifier.size(96.dp).clip(CircleShape).background(Color(0xFF6C63FF)),contentAlignment=Alignment.Center){Text("С",color=Color.White,fontSize=42.sp,fontWeight=FontWeight.Bold)};Spacer(Modifier.height(12.dp));Text("Сергей",fontSize=24.sp,fontWeight=FontWeight.Bold);Text("В сети",color=Color(0xFF2E9B5B));Spacer(Modifier.height(30.dp));OutlinedButton(onClick={}){Text("Редактировать профиль")}}}

@Composable fun ChatScreen(chat:Chat,onBack:()->Unit){var msg by remember{mutableStateOf("")};var messages by remember{mutableStateOf(listOf("Привет!","Привет 👋"))};Scaffold(topBar={TopAppBar(title={Column{Text(chat.name,fontWeight=FontWeight.Bold);Text(if(chat.online)"в сети" else "был(а) недавно",fontSize=12.sp,color=Color.Gray)}},navigationIcon={IconButton(onClick=onBack){Icon(Icons.Default.ArrowBack,"Назад")}})},bottomBar={Row(Modifier.fillMaxWidth().padding(8.dp),verticalAlignment=Alignment.CenterVertically){OutlinedTextField(msg,{msg=it},Modifier.weight(1f),placeholder={Text("Сообщение…")},shape=RoundedCornerShape(24.dp));IconButton(onClick={if(msg.isNotBlank()){messages=messages+msg;msg=""}}){Icon(Icons.Default.Send,"Отправить")}}}){p->LazyColumn(Modifier.fillMaxSize().padding(p).padding(16.dp)){items(messages){m->Row(Modifier.fillMaxWidth(),horizontalArrangement=Arrangement.End){Surface(color=Color(0xFFE9E5FF),shape=RoundedCornerShape(18.dp),modifier=Modifier.padding(vertical=4.dp)){Text(m,Modifier.padding(horizontal=14.dp,vertical=9.dp))}}}}}}

@Composable fun Avatar(name:String,online:Boolean){Box{Box(Modifier.size(54.dp).clip(CircleShape).background(Color(0xFFEEEAFE)),contentAlignment=Alignment.Center){Text(name.take(1),fontSize=22.sp,fontWeight=FontWeight.Bold,color=Color(0xFF6C63FF))};if(online)Box(Modifier.size(13.dp).clip(CircleShape).background(Color(0xFF36B37E)).align(Alignment.BottomEnd))}}
