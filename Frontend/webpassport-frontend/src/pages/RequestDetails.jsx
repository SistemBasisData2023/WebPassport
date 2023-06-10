import { PopUpModal } from "../components/PopUpModal";
import { useContext, useState } from "react";
import "../styles/requestDetails.scss"
import { AuthContext } from "../context/authContext";
import axios from "axios";
import ConfirmDialog from "./ConfirmDialog";

const RequestDetails = ({request, open, onClose}) =>{
    const {admin, currentAccount} = useContext(AuthContext);
    const [loading, setloading] = useState(false);
    const [showConfirmDialog, setshowConfirmDialog] = useState(false)

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
                        <div>
                            <p>Request ID: {request.request_id}</p>
                            <p>Date Created: {request.timestamp}</p>
                            <p>Request Schedule: {request.schedule}</p>
                            <p>Request Status: {request.status}</p>       
                        </div>
                        <div className="button-bottom">
                            {(admin != null && request.status === 'PENDING') && <button className="button-accept" onClick={() => setshowConfirmDialog(true)} disabled={loading}>{loading ? <>Loading...</> : <>ACCEPT</>}</button>}
                            <button onClick={onClose}>Close</button>
                            {(admin != null && request.status === 'PENDING') && <button className="button-decline" onClick={declineRequest} disabled={loading}>{loading ? <>Loading...</> : <>DECLINE</>}</button>}
                        </div>
                                                
                    </div>
                </PopUpModal>
                <ConfirmDialog 
                    open={showConfirmDialog} 
                    title={"REQUEST ACCEPT"}
                    onClose={() => setshowConfirmDialog(false)}
                    loading={loading}
                    message={"Accept Request?"}
                    onPositiveButton={acceptRequest}
                ></ConfirmDialog>               
            </div> 
        </div>
    )

}

export default RequestDetails;