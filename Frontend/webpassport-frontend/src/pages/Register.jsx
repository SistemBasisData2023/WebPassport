import React, {useState, useEffect} from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/register.scss"
import hidePassword from "../assets/icon/visible-off.svg"
import showPassword from "../assets/icon/visible-on.svg"

const Register = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('')
    const [password, setPassword] = useState('');
    const [isRevealedPassword, setIsRevealedPassword] = useState(false);

    const navigate = useNavigate();

    useEffect(() =>{
        sessionStorage.clear();
    },[])

    const handleUsernameChange = (event) =>{
        setUsername(event.target.value);
    }

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    }

    const handlePhoneNumberChange = (event) =>{
        setPhoneNumber(event.target.value);
    }
    
    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        // send params from input field to the server for authentication
        try{
            const response = await axios.post('http://localhost:8080/account/register',{}, 
                {params: {username: username, email: email, phoneNumber: phoneNumber, password: password}})
            if(response.status === 201){
                alert("Register success");
                console.log(response.data);
                navigate("/login")
            }
        } catch(error){
            console.log(error);
        }
    }

    return(
        <div className="register">
            <div className="card">
                <div className="left">
                    <h1>Registrasi</h1>
                    <form onSubmit={handleSubmit}>
                        <input className="input_field" placeholder="Username" type="text" 
                            name="username" value={username} onChange={handleUsernameChange}/>
                        <input className="input_field" placeholder="E-mail" type="email" 
                            name="email" value={email} onChange={handleEmailChange}/>
                        <input className="input_field" placeholder="Phone Number" type="text" 
                            name="phoneNumber" value={phoneNumber} onChange={handlePhoneNumberChange}/>
                        <div id="password_field">
                            <input className="input_field" placeholder="Password" type={isRevealedPassword ? "text":"password"} 
                            name="password" value={password} onChange={handlePasswordChange}/>
                            <img className="toggle_visible" title={isRevealedPassword ? "Hide Password" : "Show Password"} 
                            src={isRevealedPassword ? hidePassword:showPassword} onClick={() => setIsRevealedPassword(prevState => !prevState)}></img>
                        </div>
                        <button type="submit">Registrasi</button>
                    </form>
                </div>
                <div className="right">
                    <h1>WebPassport</h1>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci, voluptate? 
                        Atque quibusdam omnis aliquam adipisci asperiores nesciunt neque consectetur temporibus! 
                        Recusandae optio quas doloribus facilis id eligendi, deleniti sit inventore.
                    </p>
                    <span>Sudah Punya Akun?</span>
                    <Link to="/login"><button>Login</button></Link>
                    
                </div>
            </div>
        </div>    )
}

export default Register;