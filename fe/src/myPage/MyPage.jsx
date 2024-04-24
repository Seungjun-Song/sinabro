import React from 'react'
import styled from 'styled-components'
import MyPageSidePanel from './MyPageSidePanel'
import MyPageMainPanel from './MyPageMainPanel'
import Navbar from '../components/navs/Navbar'

const MyPageContainer = styled.div`
    display: flex;
    flex-direction: column;
    height: 100vh;
    width: 100vw;
    align-items: center;
`

const MainBox = styled.div`
    display: flex;
    width: 80%;
    height: 100%;
    margin-top: 4rem;
`

const MyPage = () => {
    return (
        <MyPageContainer>
            <div style={{ width: '100%', height: '80px'}}>
                <Navbar/>
            </div>
            <MainBox>
                <MyPageSidePanel/>
                <MyPageMainPanel/>
            </MainBox>
        </MyPageContainer>
    )
}

export default MyPage