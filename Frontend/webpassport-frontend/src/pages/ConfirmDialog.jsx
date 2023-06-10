import { PopUpModal } from "../components/PopUpModal";
import { useState } from "react";
import "../styles/confirmDialog.scss"

const ConfirmDialog = ({
    message,
    title,
    open, 
    onClose,
    onPositiveButton,
    loading = false
}) =>{
    if (!open) return null
    return(
        <div className="confirmDialog">
            <PopUpModal disableXbutton closePopUp={onClose}>
                <div className="dialog">
                    <h5 className="title">{title}</h5>
                    <div className="message">
                        <p>{message}</p>
                    </div>
                    <div className="button-bottom">
                        <button className="button-positive" onClick={onPositiveButton} disabled={loading}>{loading ? <>Loading...</> : <>YES</>}</button>
                        <button className="button-negative" onClick={onClose} disabled={loading}>{loading ? <>Loading...</> : <>NO</>}</button>
                    </div>
                </div>
                
            </PopUpModal>
        </div>
    )
}

export default ConfirmDialog;