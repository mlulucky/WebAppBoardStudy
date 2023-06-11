// alert("테스트!");
const userInsertForm=document.forms["userInsertForm"];
const uIdInput=userInsertForm.u_id;
const nameInput=userInsertForm.name;
const pwInput=userInsertForm.pw;

// uIdInput.onchange=uIdCheck;
async function uIdCheck(e){
    // 입력된 아이디 가져오기
    let uId=uIdInput.value;
    let url="./uIdCheck.do?u_id="+uId;
    const res=await fetch(url);
    if(res.status===200){
        const obj=await res.json();
        if(obj.checkId) {
            uIdInput.scrollIntoView({ behavior: "smooth" });
            uIdInput.classList.add("is-invalid");
            uIdInput.classList.remove("is-valid");
            uIdMsg.innerText="사용중인 아이디입니다."
            uIdMsg.classList.add("text-danger");
            uIdMsg.classList.remove("text-success");
            return false;
        }else if(!uId){ // 아이디가 없는 경우, 아이디 입력을 안한 경우
            uIdInput.scrollIntoView({ behavior: "smooth" });
            uIdInput.classList.add("is-invalid");
            uIdInput.classList.remove("is-valid");
            uIdMsg.innerText="아이디를 입력하세요"
            uIdMsg.classList.add("text-danger");
            uIdMsg.classList.remove("text-success");
            return false;
        }else {
            uIdInput.classList.add("is-valid");
            uIdInput.classList.remove("is-invalid");
            uIdMsg.innerText="사용가능한 아이디입니다."
            uIdMsg.classList.add("text-success");
            uIdMsg.classList.remove("text-danger");
            return true;
        }
    }else if(res.status===400){
        this.value="";
        alert(res.status+"오류입니다. 다시시도!");
    }else {
        alert(res.status+"오류입니다. 다시시도!");
        console.log(await res.text());
    }
}

function unameCheck(){
    let name=userInsertForm.name.value;
    console.log(name)
    if(name && name.trim().length>1){ // name : 값이 있는 것 //name.trim() : 공백제거 // isNaN(name) : 숫자가 아닌
        nameMsg.innerText="";
        nameInput.classList.add("is-valid");
        nameInput.classList.remove("is-invalid");
        nameMsg.innerText="사용가능한 이름";
        nameMsg.classList.add("text-success");
        nameMsg.classList.remove("text-danger");
        return true;
    }else{
        nameInput.scrollIntoView({ behavior: "smooth" });
        nameInput.classList.add("is-invalid");
        nameInput.classList.remove("is-valid");
        nameMsg.innerText="이름은 2글자 이상 입력하세요!";
        nameMsg.classList.add("text-danger");
        nameMsg.classList.remove("text-success");
        return false;
    }
}


userInsertForm.onsubmit=async function(e){
    e.preventDefault();
    let uIdState=await uIdCheck();
    let unameState=unameCheck();
    if(uIdState && unameState){
        userInsertForm.submit();
    }
}