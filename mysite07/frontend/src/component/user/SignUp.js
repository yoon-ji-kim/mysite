import React,{useState, useEffect} from 'react';
import MySiteLayout from "../../layout/MySiteLayout";
import styles from '../../assets/scss/component/user/User.scss';
import Modal from 'react-modal';
import modalStyles from '../../assets/scss/component/modal/modal.scss';
Modal.setAppElement('body');
export default function SignUp() {
    const [modalData, setModalData] = useState({isOpen: false});
    return (
        <MySiteLayout>
            <div className={styles.User}>
            <Modal
                isOpen={modalData.isOpen}
                onRequestClose={() => setModalData({isOpen: false, password: ''})}
                shouldCloseOnOverlayClick={true}
                className={modalStyles.Modal}
                overlayClassName={modalStyles.Overlay}
                style={{content: {width: 350}}}>
                <h1>비밀번호입력</h1>
                <div>
                    <form
                        onSubmit={handleSubmit}
                        className={styles.DeleteForm}
                        ref={refForm}>
                        <label>{modalData.label || ''}</label>
                        <input
                            type={'password'}
                            autoComplete={'off'}
                            name={'password'}
                            placeholder={'비밀번호'}
                            value={modalData.password}
                            onChange={(e) => setModalData(Object.assign({}, modalData, {password: e.target.value}))}
                        />
                    </form>
                </div>
                <div className={modalStyles['modal-dialog-buttons']}>
                    <button onClick={() => {
                        refForm.current.dispatchEvent(new Event("submit", {cancelable: true, bubbles: true}));
                    }}>확인
                    </button>
                    <button onClick={() => setModalData({isOpen: false, password: ''})}>취소</button>
                </div>
            </Modal>
            </div>
        </MySiteLayout>
    );
}