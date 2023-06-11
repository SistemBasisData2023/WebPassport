import React, {useState, useContext, useEffect, useMemo} from "react";
import Dropdown from "../components/Dropdown";
import Pagination from "../components/Pagination";
import ConfirmDialog from "./ConfirmDialog";
import axios from "axios";
import "../styles/adminAccountList.scss"

let PageSize = 10;

const AdminAccountList = () =>{
    const [currentPage, setCurrentPage] = useState(1);
    const [loading, setloading] = useState(false);
    const [showDeleteConfirm, setshowDeleteConfirm] = useState(false)
    const [selectedAccount, setselectedAccount] = useState([ {
            account_id: 0,
            username: "",
            email: "",
            phoneNumber: "",
            password: "",
            persons: [{
                requests:[]
            }]
        }
    ]);

    const [accountData, setaccountData] = useState([{
            account_id: 0,
            username: "",
            email: "",
            phoneNumber: "",
            password: "",
            persons: [{
                requests:[]
            }]
        }
    ]);

    const getAccount = async() =>{
        try{
            setloading(true)
            const res = await axios.get("http://localhost:8080/account/")
            setaccountData(res.data);
            setCurrentPage(2);
            setloading(false);
            return res.data
        }catch(err){
            console.log(err);
            setloading(false)
        }
    }

    const deleteAccount = async() =>{
        try{
            setloading(true)
            const res = await axios.delete(`http://localhost:8080/account/${selectedAccount.account_id}/delete`)
            if (res.status === 200){
                alert("DELETE Account Success");
                setloading(false);
                window.location.reload(false)
                return res.data;
            }
            else{
                console.log(res.data);
                setloading(false)
                return res.data
            }
            
        }catch(err){
            console.log(err);
            setloading(false)
        }
    }

    useEffect(() =>{
        getAccount();
    }, []);

    const currentTableData = useMemo( () => {
        const firstPageIndex = (currentPage - 1) * PageSize;
        const lastPageIndex = firstPageIndex + PageSize;
        return JSON.parse(JSON.stringify(accountData)).slice(firstPageIndex, lastPageIndex);
      }, [currentPage]);


    return(
        <div>
        {!loading && <div className="adminAccountList">
        <div className="table">
            <div className="table-header">
                <div id="header__id" className="header__item"><a className="filter__link" href="#">ID</a></div>
                <div id="header__name" className="header__item"><a className="filter__link filter__link--number" href="#">Username</a></div>
                <div id="header__email" className="header__item"><a className="filter__link filter__link--number" href="#">Email</a></div>
                <div id="header__phoneNumber" className="header__item"><a className="filter__link filter__link--number" href="#">Phone Number</a></div>
                <div id="header__isPersonExists" className="header__item"><a className="filter__link filter__link--number" href="#">Personal Info Exists</a></div>
                <div id="header__requestCount" className="header__item"><a className="filter__link filter__link--number" href="#">Request Count</a></div>
                <div id="header__delete" className="header__item"><a className="filter__link filter__link--number" href="#">Delete</a></div>

            </div>
            {currentTableData.map((account) => (                
                <div className="table-row" onClick={() =>{
                    console.log(account.account_id);
                    setselectedAccount(account);
                    console.log(account)
                    }}>		
                    <div id="header__id" className="table-data">{account.account_id}</div>
                    <div id="header__name" className="table-data">{account.username}</div>
                    <div id="header__email" className="table-data">{account.email}</div>
                    <div id="header__phoneNumber" className="table-data">{account.phoneNumber}</div>
                    <div id="header__isPersonExists" className="table-data">{(account.persons[0] === undefined) ? <>NO</> : <>YES</>}</div>
                    {account.persons[0] === undefined && <div id="header__requestCount" className="table-data">
                        {0}
                    </div>}
                    {account.persons[0] != undefined && <div id="header__requestCount" className="table-data">
                        {account.persons[0].requests.length}
                    </div>}
                    <div id="header__delete" className="table-data">
                        <button onClick={() => setshowDeleteConfirm(true)}>Delete</button>
                    </div>
                </div>
            ))}
            
        </div>
        <ConfirmDialog 
            open={showDeleteConfirm} 
            title={"DELETE ACCOUNT"}
            onClose={() => setshowDeleteConfirm(false)}
            loading={loading}
            message={`Delete Account ${selectedAccount.account_id} ? THIS CANNOT BE UNDONE`}
            onPositiveButton={deleteAccount}
        ></ConfirmDialog>  
        <div className="paginate">
            <Pagination
                className="pagination-bar"
                currentPage={currentPage}
                totalCount={accountData.length}
                pageSize={PageSize}
                onPageChange={page => setCurrentPage(page)}
            />                            
        </div>
        </div>}
        {loading && 
        <div style={{width: "100vw", height: "80vh", display: "flex", justifyContent: "center", alignItems: "center"}}>
            LOADING DATA...
        </div>
        }
    </div>
    )
}

export default AdminAccountList;