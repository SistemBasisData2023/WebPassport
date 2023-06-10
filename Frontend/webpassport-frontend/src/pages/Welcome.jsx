import "../styles/welcome.scss"
import {useState, useEffect, useContext} from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import hidePassword from "../assets/icon/visible-off.svg"
import showPassword from "../assets/icon/visible-on.svg"
import logo from "../assets/icon/logo.svg"
import { AuthContext } from "../context/authContext";

const Welcome = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [phoneNumber, setPhoneNumber] = useState('')
    const [loading, setLoading] = useState(false);
    const [isRevealedPassword, setIsRevealedPassword] = useState(false);
    const navigate = useNavigate();

    const [Rusername, setRUsername] = useState('')
    const [Remail, setREmail] = useState('')
    const [RPhoneNumber, setRPhoneNumber] = useState('')
    const [Rpassword, setRPassword] = useState('')


    const { currentAccount, loginAccount } = useContext(AuthContext);

    const [isLoginActive, setIsLoginActive] = useState(false);

    const handleRegisterClick = () => {
      setIsLoginActive(true);
    };
  
    const handleLoginClick = () => {
      setIsLoginActive(false);
    };
    

    useEffect(() =>{
        sessionStorage.clear();
    },[])

    const handleEmailChange = (event) => {
        setEmail(event.target.value);
    }
    
    const handlePasswordChange = (event) => {
        setPassword(event.target.value);
    }

    const handleRUsernameChange = (event) =>{
        setRUsername(event.target.value);
    }

    const handleREmailChange = (event) => {
        setREmail(event.target.value);
    }

    const handleRPhoneNumberChange = (event) =>{
        setRPhoneNumber(event.target.value);
    }
    
    const handleRPasswordChange = (event) => {
        setRPassword(event.target.value);
    }

    
    const handleSubmitLogin = async (event) => {
        event.preventDefault();
        // send the username and password to the server for authentication
        try{
            setLoading(true);
            const res = await loginAccount(email, password);
            if (res.status === 200){
                alert("Login Success");
                setLoading(false);
                console.log(res.data);
                navigate(`/account/${res.data.account_id}`)
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

    const handleSubmitRegister = async (event) => {
        event.preventDefault();
        // send params from input field to the server for authentication
        try{
            setLoading(true)
            const response = await axios.post('http://localhost:8080/account/register',{}, 
                {params: {username: Rusername, email: Remail, phoneNumber: RPhoneNumber, password: Rpassword}})
            if(response.status === 201){
                alert("Register success");
                console.log(response.data);
                setLoading(false)
                setIsLoginActive(false)
            }
        } catch(error){
            console.log(error);
            setLoading(false);
        }
    }

    
  return (
    <div style={{background: "rgb(0,0,0)"}}>
      {/* LOGIN FORM CREATION */}
      <div className="background"></div>
        <div className="container">
            <div className="item">
            <h2 className="logo">
                <i className='bx bxl-xing'></i>Web Passport
            </h2>
            <div className="text-item">
                <h2>
                Welcome! <br />
                <span>To Our App</span>
                </h2>
                <p>
                WebPassport adalah aplikasi yang memudahkan Anda untuk melakukan pemesanan paspor secara online.
                </p>
                <div className="social-icon">
                <a href="#"><i className='bx bxl-facebook'></i></a>
                <a href="#"><i className='bx bxl-twitter'></i></a>
                <a href="#"><i className='bx bxl-youtube'></i></a>
                <a href="#"><i className='bx bxl-instagram'></i></a>
                <a href="#"><i className='bx bxl-linkedin'></i></a>
                </div>
            </div>
            </div>
            <div className={`login-section ${isLoginActive ? 'active' : ''}`}>
            <div className={`form-box login`}>
                <form onSubmit={handleSubmitLogin}>
                <h2>Sign In</h2>
                <div className="input-box">
                    <span className="icon"><i className='bx bxs-envelope'></i></span>
                    <input type="text" required name="email" value={email} onChange={handleEmailChange}/>
                    <label>Username or Email</label>
                </div>
                <div className="input-box">
                    <span className="icon" style={{width: "5%", height: "5%", filter: "invert(100%) sepia(100%) saturate(0%) hue-rotate(128deg) brightness(105%) contrast(101%)"}}>
                        <img className="toggle_visible" title={isRevealedPassword ? "Hide Password" : "Show Password"} 
                            src={isRevealedPassword ? hidePassword:showPassword} onClick={() => setIsRevealedPassword(prevState => !prevState)} ></img>
                    </span>
                    <input type={isRevealedPassword ? "text":"password"} 
                            name="password" value={password} onChange={handlePasswordChange} required />       
                    <label>Password</label>
                </div>
                
                <button type="submit" className="btn" disabled={loading}>{loading ? <>Loading...</> : <>Login</>}</button>
                <div className="create-account">
                    <p>Create A New Account? <a href="#" className="register-link" onClick={handleRegisterClick}>Sign Up</a></p>
                </div>
                </form>
            </div>
            <div className={`form-box register ${!isLoginActive ? 'active' : ''}`}>
            <form onSubmit={handleSubmitRegister}>
              <h2>Sign Up</h2>
              <div className="input-box">
                <span className="icon"><i className='bx bxs-user'></i></span>
                <input type="text" required value={Rusername} onChange={handleRUsernameChange}/>
                <label>Username</label>
              </div>
              <div className="input-box">
                <span className="icon"><i className='bx bxs-envelope'></i></span>
                <input type="text" required value={Remail} onChange={handleREmailChange}/>
                <label>Email</label>
              </div>
              <div className="input-box">
                <span className="icon"><i className='bx bxs-lock-alt' ></i></span>
                <input type="text" required value={RPhoneNumber} onChange={handleRPhoneNumberChange}/>
                <label>Phone Number</label>
              </div>
              <div className="input-box">
                    <span className="icon" style={{width: "19px", height: "19px", filter: "invert(100%) sepia(100%) saturate(0%) hue-rotate(128deg) brightness(105%) contrast(101%)"}}>
                    <img className="toggle_visible" title={isRevealedPassword ? "Hide Password" : "Show Password"} 
                        src={isRevealedPassword ? hidePassword:showPassword} onClick={() => setIsRevealedPassword(prevState => !prevState)} ></img>
                    </span>
                    <input type={isRevealedPassword ? "text":"password"} 
                        name="password" value={Rpassword} onChange={handleRPasswordChange} required />       
                    <label>Password</label>
                </div>
              <div className="remember-password" >
                <label style={{fontSize: "12px"}} htmlFor=""><input type="checkbox" />I agree with this statement</label>
              </div>
              <button type="submit" className="btn" disabled={loading}>{loading ? <>Loading...</> : <>Sign Up</>}</button>
              <div className="create-account">
                <p>Already Have An Account? <a href="#" className="login-link" onClick={handleLoginClick}>Sign In</a></p>
              </div>
            </form>
          </div>          
        </div>
      </div>
    </div>
  );
}

export default Welcome;
