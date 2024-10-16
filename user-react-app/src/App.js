import React from 'react';
import {useState, useEffect} from 'react';
import logo from './logo.svg';
import './App.css';
import { call, signout } from './service/ApiService';
import MyInfo from './users/MyInfo';

function App() {

  const[user,setUser] = useState([]);
  const[modify,setModify] = useState(false);

  useEffect(() => {
    //토큰을 같이 보냄
    call("/users/name","GET")
    .then(result => setUser(result.data))
  },[]) 

  function logout (){
    signout();
  }
  
  function showMyInfo (){
    setModify(!modify);
  }

  return (
    <div className="App">
      {user.length > 0 &&(<p>{user[0].name}님환영합니다 메인화면입니다.</p>)}
      <button onClick={logout}>로그아웃</button>
      <button onClick={showMyInfo}>정보수정</button>
      {modify && <MyInfo/>}
    </div>
  );
}

export default App;
