import React from "react";

function Signup(){
    return(
        <form>
            <table border="1" align="center">
                <caption>:::회원가입:::</caption>
                <tr>
                    <th>아이디</th>
                    <td>
                        <input name="userId" id="userId"/>
                        <input type="button" value="중복체크"/>
                    </td>
                </tr>
                <tr >
                    <th>비밀번호</th>
                    <td><input name="pwd" type="password"/></td>
                </tr>
                <tr>
                    <th>이름</th>
                    <td><input name="name"/></td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td><input name="email"/></td>
                </tr>
                <tr>
                    <td colSpan="2" align="center">
                        <input type="button" value="가입"/>
                        <input type="button" value="취소"/>
                    </td>
                </tr>
            </table>
        </form>
    )
}

export default Signup;