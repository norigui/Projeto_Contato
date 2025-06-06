CREATE SEQUENCE USUARIO_SEQ
    START WITH 1
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE TBL_USUARIO (
    ID_USUARIO INTEGER DEFAULT USUARIO_SEQ.NEXTVAL NOT NULL,
    NOME VARCHAR2(100) NOT NULL,
    EMAIL VARCHAR2(100) UNIQUE NOT NULL,
    SENHA VARCHAR2(20) NOT NULL,
    ROLE VARCHAR2(50) DEFAULT 'USER'
)
