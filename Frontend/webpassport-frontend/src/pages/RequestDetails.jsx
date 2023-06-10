import { PopUpModal } from "../components/PopUpModal";
import { useContext, useEffect, useState } from "react";
import "../styles/requestDetails.scss"
import { AuthContext } from "../context/authContext";
import axios from "axios";
import ConfirmDialog from "./ConfirmDialog";

const RequestDetails = ({request, open, onClose}) =>{
    const {admin, currentAccount} = useContext(AuthContext);
    const [selectedOffice, setSelectedOffice] = useState([{
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
    }]);
    const [loading, setloading] = useState(false);
    const [showAcceptConfirmDialog, setshowAcceptConfirmDialog] = useState(false);
    const [showDeclineConfirmDialog, setshowDeclineConfirmDialog] = useState(false);

    const acceptRequest = async() =>{
        try{
            setloading(true)
            const res = await axios.put(`http://localhost:8080/request/${request.request_id}/update`,{},{
                params: {status: 'ACCEPTED'}
            });
            if (res.status === 201){
                console.log(res.data)
                alert("ACCEPT REQUEST Success");
                setloading(false);
                window.location.reload(false);
            }
            else{
                alert("ACCEPT REQUEST Failed");
                setloading(false);
            }
            
        } catch(err){
            alert("ACCEPT REQUEST Failed");
            console.log(err)
            setloading(false);
        }
    }

    const declineRequest = async() =>{
        try{
            setloading(true)
            const res = await axios.put(`http://localhost:8080/request/${request.request_id}/update`,{},{
                params: {status: 'DECLINED'}
            });
            if (res.status === 201){
                console.log(res.data)
                alert("DECLINE REQUEST Success");
                setloading(false);
                window.location.reload(false);
            }
            else{
                alert("DECLINE REQUEST Failed");
                setloading(false);
            }
            
        } catch(err){
            alert("DECLINE REQUEST Failed");
            console.log(err)
            setloading(false);
        }
    }

    

    

    if (!open) return null
    return(
        <div className="request_details">
            <div>
                <PopUpModal disableXbutton closePopUp={onClose}>
                    <div className={"requestDetails"}>
                        <h4>Informasi Request</h4>
                        <div className="detailsList">
                            <span>
                                <h5>Request ID </h5>:
                                <p>{request.request_id}</p>
                            </span>
                            <span>
                                <h5>Date Created </h5>:
                                <p>
                                    {`${((request.timestamp.split('+')[0]).split('T'))[0] }`}
                                </p>
                            </span>
                            <span>
                                <h5>Time Created </h5>:
                                <p>
                                    {`${(((request.timestamp.split('+')[0]).split('T'))[1]).split('.')[0] }`}
                                </p>
                            </span>
                            <span>
                                <h5>Request Schedule</h5>:
                                <p>
                                    {`${((request.schedule.split('+')[0]).split('T'))[0] } - 
                                    ${(((request.schedule.split('+')[0]).split('T'))[1]).split('.')[0] }`}
                                </p>
                            </span>
                            <span>
                                <h5>Request Status</h5>:
                                <h5 className={`status ${request.status}`}>{request.status}</h5>
                            </span>
                            <span>
                                <h5>Dokumen KTP</h5>:
                                <a href={`${request.documents.ktp_files.url}`}><p>{request.documents.ktp_files.name}</p></a>
                            </span>
                            <span>
                                <h5>Dokumen KK</h5>:
                                <a href={`${request.documents.kk_files.url}`}><p>{request.documents.kk_files.name}</p></a>
                            </span>

                        </div>
                        <div className="button-bottom">
                            {(admin != null && request.status === 'PENDING') && <button className="button-accept" onClick={() => setshowAcceptConfirmDialog(true)} disabled={loading}>{loading ? <>Loading...</> : <>ACCEPT</>}</button>}
                            <button onClick={onClose}>Close</button>
                            {(admin != null && request.status === 'PENDING') && <button className="button-decline" onClick={()=> setshowDeclineConfirmDialog(true)} disabled={loading}>{loading ? <>Loading...</> : <>DECLINE</>}</button>}
                        </div>
                                                
                    </div>
                </PopUpModal>
                <ConfirmDialog 
                    open={showAcceptConfirmDialog} 
                    title={"REQUEST ACCEPT"}
                    onClose={() => setshowAcceptConfirmDialog(false)}
                    loading={loading}
                    message={"Accept Request?"}
                    onPositiveButton={acceptRequest}
                ></ConfirmDialog>    
                <ConfirmDialog 
                    open={showDeclineConfirmDialog} 
                    title={"REQUEST DECLINE"}
                    onClose={() => setshowDeclineConfirmDialog(false)}
                    loading={loading}
                    message={"Decline Request?"}
                    onPositiveButton={declineRequest}
                ></ConfirmDialog>              
            </div> 
        </div>
    )

}

export default RequestDetails;