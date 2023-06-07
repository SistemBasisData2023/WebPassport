import React, {useEffect, useState, useContext} from "react"
import "../styles/request.scss"
import Dropdown from "../components/Dropdown";
import DatePicker from "react-datepicker";
import axios from "axios";
import 'react-datepicker/dist/react-datepicker.css'
import { AuthContext } from "../context/authContext";
import { useNavigate } from "react-router-dom";


const Request = () =>{
    
    const { currentAccount, setCurrentAccount } = useContext(AuthContext);
    const navigate = useNavigate()
    const [startdate, setstartdate] = useState("");
    const [loading, setLoading] = useState(false);
    const [loadingOffice, setLoadingOffice] = useState(false);
    const [scheduleDate, setScheduleDate] = useState("")
    const [ktpFiles, setKtpFiles] = useState(null);
    const [kkFiles, setKkFiles] = useState(null);
    const [selectedOfficeID, setSelectedOfficeID] = useState(0)
    const [officeData, setOfficeData] = useState([
        {
            office_id: 0,
            name: "",
            address: {
                address_id: 0,
                address_line: "",
                subDistrict: "",
                city: "",
                province: "",
                postCode: ""
            },
            requests: []
        }
    ]);

    const getOffice = async () =>{
        try{
            var res, res1;     
            if (currentAccount.persons[0] != null){
                setLoadingOffice(true);
                res = await axios.get("http://localhost:8080/office/",{
                params: {city: `${currentAccount.persons[0].address.city}`}
                });
                if(res.status === 204){
                    res1 = await axios.get("http://localhost:8080/office/")
                    if (res1.status === 200){
                        sessionStorage.setItem("office", JSON.stringify(res1.data));
                        setOfficeData(res1.data);
                        setLoadingOffice(false);
                        return res1.data;
                    }
                }
                else{
                    sessionStorage.setItem("office", JSON.stringify(res.data));
                    setOfficeData(res.data);
                    setLoadingOffice(false)
                    return res.data;
                }
                
            }
            
            
        } catch(err){
            console.log(err);
            setLoadingOffice(false)
        }
    }

    const formatDate = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        const seconds = String(date.getSeconds()).padStart(2, '0');
      
        return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
      }
      

    useEffect(() =>{
        getOffice();
    }, []);

    const handleScheduleDateChange = (e) =>{
        setstartdate(e.target.value);
        console.log(startdate);
    }
    const handleKtpFilesSelect = (e) =>{
        setKtpFiles(e.target.files[0])
    }
    const handleKkFilesSelect = (e) =>{
        setKkFiles(e.target.files[0])
    }

    const handleSubmit = async(e) =>{
        e.preventDefault();
        setLoading(true)
        const formData = new FormData();
        formData.append('office_id', selectedOfficeID);
        formData.append("schedule", scheduleDate);
        formData.append("ktp_files", ktpFiles);
        formData.append("kk_files", kkFiles);
        console.log(scheduleDate);
        try{
            const response = await axios.post(`http://localhost:8080/person/${currentAccount.persons[0].person_id}/createrequest`,
                formData, {headers: {"Content-Type": "multipart/form-data"}});
            if(response.status === 201){
                console.log(response.data);
                const changedAccount = currentAccount;
                console.log(changedAccount);
                changedAccount.persons[0].requests.push(response.data);
                setCurrentAccount(changedAccount);
                console.log(currentAccount);
                sessionStorage.setItem("account", JSON.stringify(currentAccount));
                setLoading(false)
                alert("Request Submit Success");
                navigate("/")
            }else{
                alert("Request Submit Failed")
                setLoading(false)
            }
        }
        catch(err){
            alert("Request Submit Failed");
            setLoading(false);
        }

    }

    
    const convertedJson = officeData.map(item => {
        const { address_id, ...addressInfo } = item.address;
        return {
          value: item.office_id.toString(),
          label: item.name,
          info: Object.values(addressInfo).join(', ')
        };
    });


    const options = convertedJson;
    
    return(
        <div>
        {currentAccount.persons[0] != null ? 
            <div className="request">
                Request Page
                <span>
                    <div className="field">
                        <p className="label">Select Office</p>
                        <Dropdown
                            placeHolder={loadingOffice ?  "Loading Office...": "Select..."} isSearchable={true}
                            disabled={loadingOffice}
                            options={options} onChange={(value) => setSelectedOfficeID(value.value)}>
                        </Dropdown>
                    </div>
                </span>
                <span>
                    <div className="field">
                    <p className="label">Select Schedule:</p>
                        <DatePicker showIcon={true} placeholderText="Select Schedule Date"
                            dateFormat="yyyy-MM-dd hh:mm:ss a" 
                            selected={startdate} showTimeSelect minDate={new Date()}
                            onChange={(date) => {
                                setstartdate(date);
                                setScheduleDate(formatDate(date));
                                console.log(startdate);
                                console.log(scheduleDate);
                            }}
                            onCalendarClose={()=>{
                                setScheduleDate(scheduleDate)
                                console.log(scheduleDate)
                            }} />
                    </div>
                    
                </span>
                <span>
                    <div style={{display:"flex", gap: "20px"}}><h5>KTP Files: </h5><input name="ktp_files" type="file" onChange={handleKtpFilesSelect}/></div>
                    <div style={{display:"flex", gap: "20px"}}><h5>KK Files : </h5><input type="file" onChange={handleKkFilesSelect}/></div>
                </span>
                <button id="button-bottom" disabled={loading} onClick={handleSubmit}>{loading ? <>Loading... </>: <>Submit</>}</button>    
            </div> 
                : 
            <div className="request">
                Anda Harus Mengisi Data Diri Terlebih Dahulu
                <button onClick={() => {navigate(`/account/${currentAccount.account_id}/addperson`)}}>Isi Data Diri</button>
            </div>
        }
        </div>
    )
}

export default Request;