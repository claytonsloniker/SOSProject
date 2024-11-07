import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import Modal from 'react-modal';
import '../styles/App.css';

Modal.setAppElement('#root'); // Bind modal to root element

const GameBoard = () => {
    const [board, setBoard] = useState([]);
    const [currentPlayer, setCurrentPlayer] = useState('GREEN');
    const [size, setSize] = useState(4); // Default board size set to 4
    const [inputSize, setInputSize] = useState(4); // Input field for board size set to 4
    const [gameMode, setGameMode] = useState('simple'); // Default game mode
    const [selectedLetter, setSelectedLetter] = useState('S'); // Default letter
    const [gameStatus, setGameStatus] = useState('ONGOING'); // Game status
    const [sosSequences, setSosSequences] = useState([]); // Used to hold all the sos seq
    const [showModal, setShowModal] = useState(false); // State to control modal visibility
    const [greenPlayerType, setGreenPlayerType] = useState('human'); // Green player type
    const [redPlayerType, setRedPlayerType] = useState('human'); // Red player type
    const boardRef = useRef(null);

    useEffect(() => {
        createGame(size, gameMode, greenPlayerType, redPlayerType);
        fetchGameStatus();
    }, []);

    useEffect(() => {
        drawLines(sosSequences);
        if (gameStatus === 'GREEN WON' || gameStatus === 'RED WON' || gameStatus === 'DRAW') {
            setShowModal(true);
        }
    }, [sosSequences, gameStatus]); // Draw the lines when sosSequences change

    const createGame = async (size, gameMode, greenPlayerType, redPlayerType) => {
        try {
            await axios.post('/api/game/create', null, { params: { size, gameMode, greenPlayerType, redPlayerType } });
            await fetchBoard();
            await fetchCurrentPlayer();
            await fetchGameStatus();
        } catch (error) {
            console.error('Error creating game:', error);
        }
    };

    const fetchBoard = async () => {
        try {
            const response = await axios.get('/api/game/board');
            setBoard(response.data);
        } catch (error) {
            console.error('Error fetching board:', error);
        }
    };

    const fetchCurrentPlayer = async () => {
        try {
            const response = await axios.get('/api/game/player');
            setCurrentPlayer(response.data);
        } catch (error) {
            console.error('Error fetching current player:', error);
        }
    };

    const fetchGameStatus = async () => {
        try {
            const response = await axios.get('/api/game/status');
            setGameStatus(response.data);
        } catch (error) {
            console.error('Error fetching game status:', error);
        }
    };

    const fetchSosSequences = async () => {
        try {
            const response = await axios.get('/api/game/sos-sequences');
            setSosSequences(response.data); // Use the color from the SosSequence objects
        } catch (error) {
            console.error('Error fetching SOS sequences:', error);
        }
    };

    const makeMove = async (row, col) => {
        try {
            await axios.post('/api/game/move', null, { params: { row, col, letter: selectedLetter } });
            await fetchBoard();
            await fetchCurrentPlayer();
            await fetchGameStatus();
            await fetchSosSequences();
        } catch (error) {
            console.error('Error making move:', error);
        }
    };

    const drawLines = (sosSequences) => {
        const canvas = document.getElementById('sosCanvas');
        const ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.width, canvas.height); // Clear the canvas

        sosSequences.forEach(sequence => {
            const { startRow, startCol, endRow, endCol, color } = sequence;
            ctx.strokeStyle = color;
            ctx.lineWidth = 5;
            ctx.beginPath();
            ctx.moveTo(startCol * 50 + 25, startRow * 50 + 25); // Start in the middle of the cell
            ctx.lineTo(endCol * 50 + 25, endRow * 50 + 25); // End in the middle of the cell
            ctx.stroke();
        });
    };

    const handleInputChange = (event) => {
        const value = event.target.value;
        const size = Math.max(3, Math.min(8, Number(value)));
        setInputSize(size);
    };

    const handleGameModeChange = (event) => {
        setGameMode(event.target.value);
    };

    const handleLetterChange = (letter) => {
        setSelectedLetter(letter);
    };

    const handleStartGame = () => {
        const newSize = parseInt(inputSize, 10);
        setSize(newSize);
        setSosSequences([]); // Clear the sosSequences state
        setGameStatus('ONGOING'); // Reset game status
        setShowModal(false); // Ensure modal is hidden
        const canvas = document.getElementById('sosCanvas');
        const ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.width, canvas.height); // Clear the canvas
        createGame(newSize, gameMode, greenPlayerType, redPlayerType);
    };

    const getPlayerColor = (player) => {
        return player === 'RED' ? 'red' : 'green';
    };

    const closeModal = () => {
        setShowModal(false);
    };

    return (
        <div className="game">
            <div className="player-select">
                <h1>SOS Game</h1>
                <label>
                    Board Size:
                    <input
                        type="number"
                        value={inputSize}
                        onChange={handleInputChange}
                        min="3"
                        max="8"
                    />
                </label>
            </div>
            <div className="game-mode-select">
                <label>
                    <input
                        type="radio"
                        value="simple"
                        checked={gameMode === 'simple'}
                        onChange={handleGameModeChange}
                    />
                    Simple Game
                </label>
                <label>
                    <input
                        type="radio"
                        value="general"
                        checked={gameMode === 'general'}
                        onChange={handleGameModeChange}
                    />
                    General Game
                </label>
            </div>
            <div className="player-info-container">
                <div className="player-info">
                    <h2>Green Player</h2>
                    <label>
                        <input
                            type="radio"
                            value="human"
                            checked={greenPlayerType === 'human'}
                            onChange={() => setGreenPlayerType('human')}
                        />
                        Human
                    </label>
                    <label>
                        <input
                            type="radio"
                            value="computer"
                            checked={greenPlayerType === 'computer'}
                            onChange={() => setGreenPlayerType('computer')}
                        />
                        Computer
                    </label>
                </div>
                <div className="board-wrapper" style={{ position: 'relative' }}>
                    <div className="board-container">
                        <h2 className="current-player">
                            Current Player: <span
                            style={{color: getPlayerColor(currentPlayer)}}>{currentPlayer || 'Loading...'}</span>
                        </h2>
                        <div className="board" ref={boardRef} style={{gridTemplateColumns: `repeat(${size}, 50px)`}}>
                            {board.map((row, rowIndex) => (
                                row.map((cell, colIndex) => (
                                    <div
                                        key={`${rowIndex}-${colIndex}`}
                                        onClick={() => makeMove(rowIndex, colIndex)}
                                        className="cell"
                                        style={{ width: '50px', height: '50px' }}
                                    >
                                        {cell}
                                    </div>
                                ))
                            ))}
                        </div>
                        <canvas id="sosCanvas" width={size * 50} height={size * 50}
                                style={{ position: 'absolute', top: boardRef.current?.offsetTop, left: boardRef.current?.offsetLeft, pointerEvents: 'none' }}></canvas>
                    </div>
                </div>
                <div className="player-info">
                    <h2>Red Player</h2>
                    <label>
                        <input
                            type="radio"
                            value="human"
                            checked={redPlayerType === 'human'}
                            onChange={() => setRedPlayerType('human')}
                        />
                        Human
                    </label>
                    <label>
                        <input
                            type="radio"
                            value="computer"
                            checked={redPlayerType === 'computer'}
                            onChange={() => setRedPlayerType('computer')}
                        />
                        Computer
                    </label>
                </div>
            </div>
            <div className="letter-select-and-new-game">
                <div className="letter-select">
                    <label
                        className={`radio-label-button ${selectedLetter === 'S' ? 'selected' : ''}`}
                        onClick={() => handleLetterChange('S')}
                    >
                        S
                    </label>
                    <label
                        className={`radio-label-button ${selectedLetter === 'O' ? 'selected' : ''}`}
                        onClick={() => handleLetterChange('O')}
                    >
                        O
                    </label>
                </div>
                <div className="new-game-button-container">
                    <button onClick={handleStartGame}>New Game</button>
                </div>
            </div>
            <Modal
                isOpen={showModal}
                onRequestClose={closeModal}
                contentLabel="Game Status Modal"
                className="modal"
                overlayClassName="modal-overlay"
            >
                {gameStatus === 'GREEN WON' ? (
                    <>
                        <h2>Player <span style={{color: 'green'}}>GREEN</span> has won!</h2>
                        <button className="modal-button play-again-button" onClick={handleStartGame}>Play Again</button>
                    </>
                ) : gameStatus === 'RED WON' ? (
                    <>
                        <h2>Player <span style={{color: 'red'}}>RED</span> has won!</h2>
                        <button className="modal-button play-again-button" onClick={handleStartGame}>Play Again</button>
                    </>
                ) : gameStatus === 'DRAW' ? (
                    <>
                        <h2>Draw Game!</h2>
                        <button className="modal-button play-again-button" onClick={handleStartGame}>Play Again</button>
                    </>
                ) : null}
                <button className="modal-button" onClick={closeModal}>Close</button>
            </Modal>
        </div>
    );
};

export default GameBoard;