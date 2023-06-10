import React, {useState, useContext, useEffect, useMemo} from "react";
import "../styles/home.scss"
import { AuthContext } from "../context/authContext";
import axios from "axios";
import RequestDetails from "./RequestDetails";
import Pagination from "../components/Pagination";
import { useNavigate } from "react-router-dom";

let PageSize = 4;

const Home = () =>{
    const [currentPage, setCurrentPage] = useState(1);
    const { currentAccount, admin, loginAccount, setCurrentAccount } = useContext(AuthContext);
    const [ currentRequest, setCurrentRequest] = useState(null);
    const [showRequestDetails, setshowRequestDetails] = useState(false);
    const navigate = useNavigate()
    const getAccount = async() =>{
        try{
            const response = await axios.get(`http://localhost:8080/account/${currentAccount.account_id}/info`);
            console.log(response.data);
            setCurrentAccount(response.data);
            return response.data
            
        } catch(err){
            console.error(err);
        }
    }

    const getRequest=()=>{
        if (currentAccount.persons[0] == undefined) return null
        return currentAccount.persons[0].requests;
    }

    const currentTableData = useMemo(() => {
            if (getRequest() == null) return null;
            const firstPageIndex = (currentPage - 1) * PageSize;
            const lastPageIndex = firstPageIndex + PageSize;
            return JSON.parse(JSON.stringify(getRequest())).slice(firstPageIndex, lastPageIndex);
        }, [currentPage]);

    return(
        <div className="home">
            <div >
                {(currentAccount.persons[0] != undefined && currentAccount.persons[0].requests != undefined) ? 
                <div className="listRequest">
                    <h4>List Request:</h4>
                    <div className="requestExists">
                    {currentTableData.map((request) =>(
                    <div className={`request_field ${request.status}`} onClick={()=> {
                        console.log(request);
                        setCurrentRequest(request)
                        setshowRequestDetails(true)
                    }}>
                        <p>Time Created: {request.timestamp.split('+')[0]}</p>
                        <p>Schedule Date: {(request.schedule.split('+')[0]).split('T')[0]}</p>
                        <p>Schedule Time: {(request.schedule.split('+')[0]).split('T')[1]}</p>
                        <p>Status: {request.status}</p>
                </div>)
            )}
                    </div>
                </div>
                 : 
            <div className="noRequest">
                <div>Belum ada Request</div>
                <button onClick={() => {navigate(`/account/${currentAccount.account_id}/addrequest`)}}>Add Request</button>
            </div>}
            </div>
            <div className="paginate">
                <Pagination
                    className="pagination-bar"
                    currentPage={currentPage}
                    totalCount={getRequest() != null ? getRequest().length :0}
                    pageSize={PageSize}
                    onPageChange={page => setCurrentPage(page)}
                />                            
            </div>

            <RequestDetails 
                open={showRequestDetails} onClose={()=>setshowRequestDetails(false)} 
                request={currentRequest}>
            </RequestDetails>
            
        </div>
    )
}

export default Home;