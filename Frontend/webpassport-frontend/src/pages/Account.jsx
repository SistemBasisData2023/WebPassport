import React, {useState, useContext, useEffect} from "react";
import { Link } from "react-router-dom";
import "../styles/account.scss"
import hidePassword from "../assets/icon/visible-off.svg"
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css'
import showPassword from "../assets/icon/visible-on.svg"
import { AuthContext } from "../context/authContext";
import axios from "axios";
import Dropdown from "../components/Dropdown";

let PageSize = 5;

const Account = () =>{

    const { currentAccount, setCurrentAccount } = useContext(AuthContext);
    const [visible, setVisible] = useState(false);
    const [disable, setDisabled] = useState(true);
    const [loading, setLoading] = useState(false);
    const [accountPassword, setAccountPassword] = useState(null);
    const [isRevealedPassword, setIsRevealedPassword] = useState(false);
    const [enableEditPass, setEnableEditPass] = useState(false);
    const [currentPage, setCurrentPage] = useState(1);


    const [accountInfo, setAccountInfo] = useState({
        ...currentAccount
    });

    const [personInfo, setPersonInfo] = useState({
        person_id: "",
        name: "",
        nik: "",
        date_of_birth: "",
        place_of_birth: "",
        address_id: "",
        address_line: "",
        subDistrict: "",
        city: "",
        province: "",
        postCode: ""
        
    });

    const options = [
        { value: "MALE", label: "MALE" },
        { value: "FEMALE", label: "FEMALE" }
      ];

      useEffect(() =>{
        if (currentAccount.persons[0] != undefined){
            setPersonInfo({
                person_id: currentAccount.persons[0].person_id,
                name: currentAccount.persons[0].name,
                nik: currentAccount.persons[0].nik,
                date_of_birth: currentAccount.persons[0].date_of_birth,
                place_of_birth: currentAccount.persons[0].place_of_birth,
                gender: currentAccount.persons[0].gender,
                address_id: currentAccount.persons[0].address.address_id,
                address_line: currentAccount.persons[0].address.address_line,
                subDistrict: currentAccount.persons[0].address.subDistrict,
                city: currentAccount.persons[0].address.city,
                province: currentAccount.persons[0].address.province,
                postCode: currentAccount.persons[0].address.postCode
            });
        }
    }, [currentAccount]);

    const accountDefaultValue = () =>{
        setAccountInfo({
            ...currentAccount
        })
    }

    const personDefaultValue = () =>{
        setPersonInfo({
            person_id: currentAccount.persons[0].person_id,
            name: currentAccount.persons[0].name,
            nik: currentAccount.persons[0].nik,
            date_of_birth: currentAccount.persons[0].date_of_birth,
            place_of_birth: currentAccount.persons[0].place_of_birth,
            gender: currentAccount.persons[0].gender,
            address_id: currentAccount.persons[0].address.address_id,
            address_line: currentAccount.persons[0].address.address_line,
            subDistrict: currentAccount.persons[0].address.subDistrict,
            city: currentAccount.persons[0].address.city,
            province: currentAccount.persons[0].address.province,
            postCode: currentAccount.persons[0].address.postCode
        });
    };

    const handleAccountChange = (e) =>{
        setAccountInfo({...accountInfo, [e.target.name]: e.target.value});
    }

    const handlePersonChange = (e) =>{
        setPersonInfo({...personInfo, [e.target.name]: e.target.value});
        console.log(personInfo)
    }
    const handlePasswordChange = (e) =>{
        setAccountPassword(e.target.value);
    }

    const toddMMyyyy = (date) =>{
        return `${String(date.getDate()).padStart(2, "0")}-${String(date.getMonth() + 1).padStart(2, "0")}-${date.getFullYear()}`;
    }
    
    var date, dateString;
    if (currentAccount.persons[0] != undefined ){
        date = new Date(personInfo.date_of_birth);
        date.setHours(date.getHours() + (date.getTimezoneOffset() / 60) + 7);
        dateString = `${String(date.getDate()).padStart(2, "0")}-${String(date.getMonth() + 1).padStart(2, "0")}-${date.getFullYear()}`;
    }

    const toggleVisible = () =>{
        setVisible(!visible);
    }
    const toggleDisable = () =>{
        setDisabled(!disable);
    }
    const handleEditClick = () =>{
        toggleVisible();
        toggleDisable();
    }
    const toggleCancelEditClick = () =>{
        handleEditClick();
        accountDefaultValue();
        personDefaultValue();
        setEnableEditPass(false);
        window.location.reload(false);
    }
    
    const handleSubmitChange = async (e) =>{
        e.preventDefault();
        const addressBody = {
            address_id: personInfo.address_id,
            address_line: personInfo.address_line,
            subDistrict: personInfo.subDistrict,
            city: personInfo.city,
            province: personInfo.province,
            postCode: personInfo.postCode
        };
        const personBody = {
            person_id: personInfo.person_id,
            name: personInfo.name,
            nik: personInfo.nik,
            date_of_birth: personInfo.date_of_birth,
            place_of_birth: personInfo.place_of_birth,
            gender: personInfo.gender,
        };
        const accountBody = {
            username: accountInfo.username,
            email: accountInfo.email,
            phoneNumber: accountInfo.phoneNumber,
            password: accountPassword
        };
        try{
            setLoading(true);
            const addressResponse = await axios.put("http://localhost:8080/address/update", addressBody, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            });
            const personResponse = await axios.put(`http://localhost:8080/person/${personInfo.person_id}/update`, personBody, {
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            });
            const accountResponse = await axios.put(`http://localhost:8080/account/${currentAccount.account_id}/update`, accountBody,{
                headers: {
                    "Content-Type": "multipart/form-data",
                }
            });
            if(accountResponse.status === 201){
                setLoading(false);
                alert("Edit Account Information Success");
                setCurrentAccount(accountResponse.data);
                window.location.reload(false);
            }
            else{
                setLoading(false);
                alert("Edit Account Information Failed");
            }
            console.log(addressResponse, personResponse, accountResponse);

        } catch(err){
            setLoading(false);
            console.log(err);
            alert("Edit Account Information Failed");
        }
    }

    return(
        <div className="account">
            <h5>Informasi Akun</h5>
            <div className="account_info">
                <span>
                    <div className="field">
                        <p className="label">Username </p>:
                        <input name="username" value={accountInfo.username} disabled={disable} onChange={handleAccountChange}/>
                    </div>
                    {enableEditPass && <div className="field">
                        <p className="label">Old Password </p>:
                        <div id="password_field">
                            <input className="input_field" placeholder="Password" type={isRevealedPassword ? "text":"password"} 
                            name="accountPassword"/>
                            <img className="toggle_visible" title={isRevealedPassword ? "Hide Password" : "Show Password"} 
                            src={isRevealedPassword ? hidePassword:showPassword} onClick={() => setIsRevealedPassword(prevState => !prevState)}></img>
                        </div>
                    </div>}
                    
                </span>
                <span>
                    <div className="field">
                        <p className="label">Email </p>:
                        <input name="email" value={accountInfo.email} disabled={disable} onChange={handleAccountChange}/>
                    </div>
                    {enableEditPass && <div className="field">
                        <p className="label">New Password </p>:
                        <div id="password_field">
                            <input className="input_field" placeholder="Password" type={isRevealedPassword ? "text":"password"} 
                            name="accountPassword"/>
                            <img className="toggle_visible" title={isRevealedPassword ? "Hide Password" : "Show Password"} 
                            src={isRevealedPassword ? hidePassword:showPassword} onClick={() => setIsRevealedPassword(prevState => !prevState)}></img>
                        </div>
                    </div>}
                </span>
                <span>
                    <div className="field">
                        <p className="label">Phone Number </p>:
                        <input name="phoneNumber" type="number" value={accountInfo.phoneNumber} disabled={disable} onChange={handleAccountChange}/>
                    </div>
                    {enableEditPass && <div className="field">
                        <p className="label">Confirm Password </p>:
                        <div id="password_field">
                            <input className="input_field" placeholder="Password" type={isRevealedPassword ? "text":"password"} 
                            name="accountPassword" value={accountPassword} onChange={handlePasswordChange}/>
                            <img className="toggle_visible" title={isRevealedPassword ? "Hide Password" : "Show Password"} 
                            src={isRevealedPassword ? hidePassword:showPassword} onClick={() => setIsRevealedPassword(prevState => !prevState)}></img>
                        </div>
                    </div>}
                </span>
            </div>
            <div>{currentAccount.persons[0] != undefined ? 
                    <div className="person_info">
                        <span>
                            <div className="field">
                                <p className="label">Full Name </p>:
                                <input name="name" value={personInfo.name} disabled={disable} onChange={handlePersonChange}/>
                            </div>
                            <div className="field">
                                <p className="label">NIK </p>:
                                <input 
                                    name="nik" type="number"  
                                    max="16" value={personInfo.nik} 
                                    disabled={disable} onChange={handlePersonChange}/>
                            </div>
                        </span>
                        <span>
                            <div className="field">
                                <p className="label">Date of Birth </p>:
                                <input name="date_of_birth" value={personInfo.date_of_birth.split("T")[0]} disabled={disable} onChange={handlePersonChange}/>
                            </div>
                            <div className="field">
                                <p className="label">Place of Birth </p>:
                                <input name="place_of_birth" value={personInfo.place_of_birth} disabled={disable} onChange={handlePersonChange}/>
                            </div>  
                        </span>
                        <span>
                            <div className="field">
                                <p className="label">Gender </p>:
                                <Dropdown className="dropdown" disabled={disable}
                                    placeHolder={personInfo.gender}
                                    options={options} onChange={(value) => personInfo.gender = value.value}>
                                </Dropdown>
                            </div>                           
                        </span>
                        <span>
                            <div className="field">
                                <p className="label">Address Line </p>:
                                <input name="address_line" className="address_line" value={personInfo.address_line} disabled={disable} onChange={handlePersonChange}/>
                            </div>
                        </span>
                        <span>
                            <div className="field">
                                <p className="label">Sub District </p>:
                                <input name="subDistrict" value={personInfo.subDistrict} disabled={disable} onChange={handlePersonChange}/>
                            </div>
                            
                            <div className="field">
                                <p className="label">City </p>:
                                <input name="city" value={personInfo.city} disabled={disable} onChange={handlePersonChange}/>
                            </div>
                        </span>
                        <span>
                            <div className="field">
                                <p className="label">Province </p>:
                                <input name="province" value={personInfo.province} disabled={disable} onChange={handlePersonChange}/>
                            </div>
                            <div className="field">
                                <p className="label">Post Code </p>:
                                <input name="postCode" type="number" maxLength={6} value={personInfo.postCode} disabled={disable} onChange={handlePersonChange}/>
                            </div>
                        </span>
                        {!visible &&
                        <div style={{display: "flex", flexDirection: "column", alignItems: "center"}}>
                            <span>
                                <button onClick={handleEditClick}>Edit Account Information</button>
                            </span>
                        </div>}
                        {visible && 
                        <div style={{display: "flex", flexDirection: "column", gap: "10px"}}>
                            <label>
                                <input className="checkPass" type="checkbox" 
                                    onChange={() => setEnableEditPass(enableEditPass => !enableEditPass)}
                                />
                                Enable Password Edit
                            </label>
                            <span>
                                <button onClick={toggleCancelEditClick} disabled={loading}>Cancel</button>
                                <button className="button submit" onClick={handleSubmitChange} disabled={loading}>{loading ? <>Loading...</> : <>Submit Change</>}</button>
                            </span>
                        </div>}
                    </div> 
                        : 
                    <div className="person_not_found">
                            <p>Anda harus mengisi data diri terlebih dahulu sebelum melakukan pemesanan passport</p>
                        <Link to={`/account/${currentAccount.account_id}/addperson`}><button >Isi Data Diri</button></Link>
                        
                    </div>}
                </div>
        </div>
    );
}

export default Account;