import {useState, useEffect, useContext} from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/login.scss"
import hidePassword from "../assets/icon/visible-off.svg"
import showPassword from "../assets/icon/visible-on.svg"
import { AuthContext } from "../context/authContext";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [isRevealedPassword, setIsRevealedPassword] = useState(false);
    const navigate = useNavigate();

    const { loginAccount } = useContext(AuthContext);

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    }
    
    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handleSubmit = async (event) => {
        event.preventDefault();
        // send the username and password to the server for authentication
        try{
            const res = await loginAccount(email, password);
            if (res.status === 200){
                alert("Login Success");
                console.log(res.data);
                navigate("/")
            }
            else{
                alert("Login Failed")
            }
        } catch(error){
            alert("Login Failed")
            console.log(error);
        }
    }

    return(
        <div className="login">
            <div className="card">
                <div className="left">
                    <h1>WebPassport</h1>
                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Adipisci, voluptate? 
                        Atque quibusdam omnis aliquam adipisci asperiores nesciunt neque consectetur temporibus! 
                        Recusandae optio quas doloribus facilis id eligendi, deleniti sit inventore.
                    </p>
                    <span>Don't Have an Account?</span>
                    <Link to="/register"><button>Register</button></Link>
                    
                </div>
                <div className="right">
                    <h1>Login</h1>
                    <form onSubmit={handleSubmit}>
                        <input id="email_field" className="input_field" placeholder="Username or E-mail" type="text" 
                            name="email" value={email} onChange={handleEmailChange}/>
                        <div id="password_field">
                            <input className="input_field" placeholder="Password" type={isRevealedPassword ? "text":"password"} 
                            name="password" value={password} onChange={handlePasswordChange}/>
                            <img className="toggle_visible" title={isRevealedPassword ? "Hide Password" : "Show Password"} 
                            src={isRevealedPassword ? hidePassword:showPassword} onClick={() => setIsRevealedPassword(prevState => !prevState)}></img>
                        </div>
                        
                        <button id="submit_field" type="submit">Login</button>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Login;