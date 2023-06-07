import {useState, useEffect, useContext} from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "../styles/login.scss"
import hidePassword from "../assets/icon/visible-off.svg"
import showPassword from "../assets/icon/visible-on.svg"
import logo from "../assets/icon/logo.svg"
import { AuthContext } from "../context/authContext";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [isRevealedPassword, setIsRevealedPassword] = useState(false);
    const navigate = useNavigate();

    const { loginAccount } = useContext(AuthContext);

    useEffect(() =>{
        sessionStorage.clear();
    },[])

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
            setLoading(true);
            const res = await loginAccount(email, password);
            if (res.status === 200){
                alert("Login Success");
                setLoading(false);
                console.log(res.data);
                navigate("/")
            }
            else{
                alert("Login Failed");
                console.log(res.data)
                setLoading(false);
            }
        } catch(error){
            alert("Login Failed");
            console.log(error);
            setLoading(false);
        }
    }

    return(
        <div className="login">
            <div className="card">
                <div className="left">
                    <img src={logo} style={{color: "white", width: "60%", filter: "brightness(0) saturate(100%) invert(98%) sepia(5%) saturate(2%) hue-rotate(59deg) brightness(110%) contrast(100%)"}}/>
                    <p>WebPassport adalah aplikasi yang memudahkan Anda untuk melakukan pemesanan paspor secara online.
                        
                    </p>
                    <span>Belum punya akun?</span>
                    <Link to="/register"><button>Registrasi</button></Link>
                    
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
                        
                        <button id="submit_field" type="submit">{loading ? <>Loading...</> : <>Login</>}</button>
                    </form>
                </div>
            </div>
        </div>
    )
}

export default Login;