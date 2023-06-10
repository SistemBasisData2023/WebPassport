import React, {useState, useContext} from "react";
import "../styles/addPerson.scss"
import { AuthContext } from "../context/authContext";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css'
import Dropdown from "../components/Dropdown";

const AddPerson = () =>{
    const { currentAccount, setCurrentAccount } = useContext(AuthContext);
    const navigate = useNavigate();
    const [personInfo, setPersonInfo] = useState({
        name: "",
        nik: "",
        date_of_birth: "",
        place_of_birth: "",
        address_line: "",
        subDistrict: "",
        city: "",
        province: "",
        postCode: ""
        
    });

    const toddMMyyyy = (date) =>{
        return `${String(date.getDate()).padStart(2, "0")}-${String(date.getMonth() + 1).padStart(2, "0")}-${date.getFullYear()}`;
    }

    const range = (start, stop, step) => Array.from(
        { length: (stop - start) / step + 1 },
        (value, index) => start + index * step);

    const years = range(2023, 1923, -1);
    const months = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
    ];

    const handleChange = (e) => {
        const value = e.target.value;
        setPersonInfo({...personInfo, [e.target.name]: value});
        console.log(personInfo);
    };

    const cancelSubmit = () =>{
        navigate(-1);
    }

    const handleSubmit = async(event) =>{
        event.preventDefault();
        try{
            const res = await axios.post(`http://localhost:8080/account/${currentAccount.account_id}/addperson`,{},{
                params:{
                    name: personInfo.name, 
                    nik: personInfo.nik,
                    date_of_birth: personInfo.date_of_birth,
                    place_of_birth: personInfo.place_of_birth,
                    gender: personInfo.gender,
                    address_line: personInfo.address_line,
                    subDistrict: personInfo.subDistrict,
                    city: personInfo.city,
                    province: personInfo.province,
                    postCode: personInfo.postCode
                }
            });
            if(res.status === 201){
                alert("Add Person Success");
                console.log(res.data);
                setCurrentAccount(res.data);
                navigate(`/account/${currentAccount.account_id}`);
            }
            else{
                alert("Add Person Failed");
            }
        }catch(err){
            alert("Add Person Failed");
            console.log(err);
        }
        


    }

    return(
        <div className="addPerson">
            <div className="person_info">Isi Data Diri
            <form onSubmit={handleSubmit}>
                <span>
                    <div className="field">
                        <p className="label">Nama Lengkap :</p>
                        <input name="name" required type="text" value={personInfo.name} onChange={handleChange}/> 
                    </div>
                    <div className="field">
                        <p className="label">NIK: </p>
                        <input name="nik" required type="text" value={personInfo.nik} onChange={handleChange}/> 
                    </div>
                    
                    
                </span>
                <span>
                <div className="field">
                    <p className="label">Tanggal Lahir: </p>
                    <DatePicker renderCustomHeader={({
                                date,changeYear,
                                changeMonth,decreaseMonth,
                                increaseMonth,prevMonthButtonDisabled,
                                nextMonthButtonDisabled,
                                }) => (
                                    <div
                                    style={{
                                        margin: 10,
                                        display: "flex",
                                        justifyContent: "center",
                                    }}
                                    >
                                    <button onClick={decreaseMonth} disabled={prevMonthButtonDisabled}>
                                        {"<"}
                                    </button>
                                    <select
                                        value={date.getFullYear()}
                                        onChange={({ target: { value } }) => changeYear(value)}
                                    >
                                        {years.map((option) => (
                                        <option key={option} value={option}>
                                            {option}
                                        </option>
                                        ))}
                                    </select>

                                    <select
                                        value={months[date.getMonth()]}
                                        onChange={({ target: { value } }) =>
                                        changeMonth(months.indexOf(value))
                                        }
                                    >
                                        {months.map((option) => (
                                        <option key={option} value={option}>
                                            {option}
                                        </option>
                                        ))}
                                    </select>

                                    <button onClick={increaseMonth} disabled={nextMonthButtonDisabled}>
                                        {">"}
                                    </button>
                                    </div>
                                )} showIcon={true} placeholderText="dd-MM-yyyy" required
                                    dateFormat="dd-MM-yyyy"
                                    selected={personInfo.date_of_birth} maxDate={new Date()} 
                                    onChange={(date) => setPersonInfo({date_of_birth: date})}/>
                </div>
                <div className="field">
                    <p className="label">Tempat Lahir: </p>
                    <input name="place_of_birth" type="text" required value={personInfo.place_of_birth} onChange={handleChange}/>
                </div>
                </span>
                <span>
                    <div className="field">
                        <p className="label">Gender: </p>
                        <Dropdown className="dropdown" 
                            placeHolder={"Select..."}
                            options={[
                                { value: "MALE", label: "LAKI-LAKI" },
                                { value: "FEMALE", label: "PEREMPUAN" }
                              ]} onChange={(value) => personInfo.gender = value.value}>
                        </Dropdown>
                    </div>
                    
                </span>
                <span>
                    <div className="field address">
                        <p className="label">Alamat: </p>
                        <input className="address_line" required type="text" name="address_line" value={personInfo.address_line} onChange={handleChange}/>
                    </div>
                </span>
                <span>
                    <div className="field">
                        <p className="label">Kecamatan: </p>
                        <input name="subDistrict" required type="text" value={personInfo.subDistrict} onChange={handleChange}/>
                    </div>                   
                    <div className="field">
                        <p className="label">Kota/ Kabupaten: </p>
                        <input name="city" type="text" required value={personInfo.city} onChange={handleChange}/>
                    </div>
                </span>
                <span>
                    <div className="field">
                        <p className="label">Provinsi: </p>
                        <input name="province" type="text" required value={personInfo.province} onChange={handleChange}/>
                    </div>
                    <div className="field">
                        <p className="label">Kode Pos: </p>
                        <input name="postCode" type="text" required value={personInfo.postCode} onChange={handleChange}/>
                    </div>
                    
                </span>
                <span className="button">
                    <button className="button-bottom oke" type="submit">OK</button>
                    <button className="button-bottom cancel" onClick={cancelSubmit}>Batal</button>
                </span>
            </form>
                
                
            </div> 
        </div>
    );
}

export default AddPerson