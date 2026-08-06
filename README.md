# ☕ Studios Veneto — Backend

**API REST em Spring Boot** responsável por servir o conteúdo dinâmico da landing page do empreendimento Studios Veneto (MK2 Incorporadora) e capturar leads, com notificação automática por e-mail para o time de corretores.

> 🔗 Repositório do frontend: [`estudio-veneto-frontend`](https://github.com/caiomilanic/estudio-veneto-frontend)

---

## 📖 Sobre

Este backend serve como fonte única de dados para a landing page — textos institucionais, fotos, preços, diferenciais e links sociais são todos consultados via API, sem hardcode no frontend. Também é responsável por capturar cada lead preenchido no formulário e notificar o corretor automaticamente por e-mail.

---

## 📑 Índice

- [Tecnologias](#️-tecnologias)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Endpoints da API](#-endpoints-da-api)
- [Como rodar localmente](#-como-rodar-localmente)
- [Variáveis de ambiente](#-variáveis-de-ambiente)
- [Roadmap](#-roadmap)
- [Aprendizados técnicos](#-aprendizados-técnicos)

---

## 🛠️ Tecnologias

| Tecnologia | Uso |
|---|---|
| ☕ **Java 25** (LTS) | Linguagem principal |
| 🍃 **Spring Boot 4.1** | Framework da API REST |
| 🐘 **PostgreSQL** (via [Neon](https://neon.tech)) | Banco de dados serverless, região `sa-east-1` |
| 📧 **[Brevo](https://brevo.com)** | Envio de e-mails transacionais (SMTP relay) |
| ✅ **Bean Validation** | Validação de DTOs de entrada |
| 📦 **Maven** | Gerenciador de dependências e build |

---

## 📂 Estrutura do projeto

Organização **por feature** (não por camada técnica) — cada módulo carrega seus próprios `dto/`, `entity/`, `Controller`, `Service` e `Repository`:

```
com.estudioveneto.landingapi
│
├── 📁 content/                  → conteúdo institucional da landing
│   ├── 📁 dto/                   ContentDTO, PhotoDTO, SocialLinkDTO, HighlightDTO
│   ├── 📁 entity/                 SiteContent, Photo, SocialLink, Highlight
│   ├── ContentController.java     GET /api/content, /photos, /social-links, /highlights
│   ├── ContentService.java
│   └── *Repository.java
│
├── 📁 pricing/                  → tipologias e valores
│   ├── 📁 dto/                    UnitDTO
│   ├── 📁 entity/                 Unit
│   ├── UnitController.java        GET /api/units
│   ├── UnitService.java
│   └── UnitRepository.java
│
├── 📁 lead/                     → captação de leads
│   ├── 📁 dto/                    LeadRequestDTO (com Bean Validation)
│   ├── 📁 entity/                 Lead
│   ├── LeadController.java        POST /api/leads
│   ├── LeadService.java           ✉️ salva no banco + dispara e-mail via Brevo
│   └── LeadRepository.java
│
├── 📁 config/
│   └── CorsConfig.java            libera a origem do frontend (Vite)
│
├── 📁 exception/
│   └── GlobalExceptionHandler.java
│
└── LandingApiApplication.java
```

---

## 🔌 Endpoints da API

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/api/content` | Textos institucionais (hero, sobre, localização, para-morar, para-investir, sobre-incorporadora) |
| `GET` | `/api/photos` | Fotos do empreendimento (URLs Cloudinary), ordenadas por `displayOrder` |
| `GET` | `/api/social-links` | Instagram e WhatsApp (número + mensagem padrão) |
| `GET` | `/api/highlights?category=` | Lista de destaques — `category=localizacao` ou `category=diferenciais` |
| `GET` | `/api/units` | Tipologias disponíveis (Studio / Studio Garden), com metragem e preço |
| `POST` | `/api/leads` | 📨 Captura um novo lead e dispara notificação por e-mail para o corretor |

<details>
<summary>📦 Exemplo de payload — <code>POST /api/leads</code></summary>

```json
{
  "nome": "Maria Silva",
  "telefone": "(41) 99999-9999",
  "email": "maria@exemplo.com"
}
```

**Validações aplicadas:**
- `nome` — obrigatório
- `telefone` — obrigatório
- `email` — obrigatório e formato válido
</details>

<details>
<summary>📦 Exemplo de resposta — <code>GET /api/units</code></summary>

```json
[
  { "tipo": "Studio", "metragem": 18.0, "precoAPartirDe": 199900.00 },
  { "tipo": "Studio Garden", "metragem": 19.0, "precoAPartirDe": 219900.00 }
]
```
</details>

---

## 🚀 Como rodar localmente

### Pré-requisitos
- ☕ Java 25
- 📦 Maven (ou use o wrapper `./mvnw` incluído)
- 🐘 Conta no [Neon](https://neon.tech) — Postgres serverless
- 📧 Conta no [Brevo](https://brevo.com) — SMTP transacional, com remetente verificado

### Passos

```bash
git clone https://github.com/caiomilanic/estudio-veneto-backend.git
cd estudio-veneto-backend

# configure as variáveis de ambiente (ver seção abaixo)
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. Teste com:
```
http://localhost:8080/api/content
```

---

## 🔐 Variáveis de ambiente

| Variável | Descrição |
|---|---|
| `DB_HOST` | Endpoint **pooled** do Neon (ex: `ep-xxx-pooler.sa-east-1.aws.neon.tech`) |
| `DB_NAME` | Nome do banco (`neondb`) |
| `DB_USER` | Usuário do Postgres |
| `DB_PASSWORD` | Senha do Postgres |
| `BREVO_SMTP_USER` | Login SMTP do Brevo (formato `xxxxxxx001@smtp-brevo.com`) |
| `BREVO_SMTP_KEY` | Chave SMTP gerada no painel do Brevo |
| `LEAD_SENDER_EMAIL` | E-mail remetente, **verificado** no Brevo (Settings → Senders & IP) |
| `CORRETOR_EMAIL` | E-mail que recebe a notificação de cada novo lead |

> ⚠️ **Importante:** configure essas variáveis diretamente na *Run Configuration* da sua IDE (IntelliJ: Run → Edit Configurations → Environment Variables). Definir só no terminal via `export` não é suficiente se você rodar a aplicação pela IDE — o Spring resolve `${DB_HOST}` literalmente e quebra com `UnknownHostException`.

> 🔒 Nunca commite `application-local.properties` nem qualquer arquivo com credenciais reais — já estão no `.gitignore`.

---

## 🗺️ Roadmap

### ✅ Concluído
- [x] Endpoints de conteúdo (`content`, `photos`, `social-links`, `highlights`, `units`)
- [x] Captura de leads com validação
- [x] Notificação automática por e-mail via Brevo
- [x] CORS configurado para o frontend
- [x] Dados reais do empreendimento populados no Neon

### 🚧 Pendente
- [ ] ☁️ Hospedagem em produção (Render ou Railway)
- [ ] ✉️ Migração do remetente de e-mail para domínio próprio, com autenticação SPF/DKIM
- [ ] 🧪 Teste de carga/monitoramento básico (Actuator)

---

## 💡 Aprendizados técnicos

- 🌍 Variáveis de ambiente devem ser configuradas **na IDE**, não apenas no terminal — senão o Spring resolve `${DB_HOST}` literalmente e lança `UnknownHostException`
- 📧 No Brevo, **login SMTP ≠ e-mail remetente** — são credenciais distintas. O remetente precisa ser verificado separadamente em *Settings → Senders & IP*
- 🔌 O endpoint **pooled** do Neon (com sufixo `-pooler`) é o correto para uma aplicação web que abre/fecha conexões constantemente — evita esgotar o limite de conexões diretas do Postgres
- 🔧 CORS precisa liberar explicitamente a origem do Vite (`http://localhost:5173` em dev)

---

## 🏢 Sobre a incorporadora

**MK2 Incorporadora** — atuação desde 2010, desenvolvendo empreendimentos residenciais pautados em planejamento, responsabilidade e qualidade construtiva.

---

<p align="center">Feito com ☕ e 🍃 Spring Boot</p>