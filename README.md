Logic:

If:

```html
  <script>
	  let user = { loggedIn: false };

	  function toggle() {
	    user.loggedIn = !user.loggedIn;
	  }
  </script>

  {#if user.loggedIn}
	  <button on:click={toggle}>
		  Log out
	  </button>
  {/if}

  {#if !user.loggedIn}
	  <button on:click={toggle}>
		  Log in
	  </button>
  {/if}

```
Else:
```diff
  <script>
	  let user = { loggedIn: false };

	  function toggle() {
	    user.loggedIn = !user.loggedIn;
	  }
  </script>

  {#if user.loggedIn}
	  <button on:click={toggle}>
		  Log out
	  </button>
- {/if}
- {#if !user.loggedIn}
+ {:else}
	  <button on:click={toggle}>
		  Log in
	  </button>
  {/if}

```
Else if:
```html
  <script>
	  let x = 3;
  </script>

  {#if x >= 10}
	  <p>{x} es mayor o igual a 10</p>
  {:else if x < 5 && x >= 0}
	  <p>{x} es menor a 5 y mayor o igual a 0</p>
  {:else if x >= 5 && x < 10}
	  <p>{x} está entre 5 y 10</p>
  {:else}
	  <p>{x} es negativo </p>
  {/if}

  {#if x % 2 == 0}
	  <p>{x} es par </p>
  {:else}
	  <p>{x} es impar </p>
  {/if}
```
Each:
```html
  <script>
	  let cats = [
	    { id: "J---aiyznGQ", name: "Keyboard Cat" },
	    { id: "z_AbfPXTKms", name: "Maru" },
	    { id: "OUtn3pvWmpg", name: "Henri The Existential Cat" }
	  ];
  </script>

  <h1>The Famous Cats of YouTube</h1>

  <ul>
	  {#each cats as cat, i}
		  <li><a target="_blank" href="https://www.youtube.com/watch?v={cat.id}">
			  {i + 1}: {cat.name}
		  </a></li>
	  {/each}
  </ul>

  <ul>
	  {#each cats as {id,name}, i}
		  <li><a target="_blank" href="https://www.youtube.com/watch?v={id}">
			  {i + 1}: {name}
		  </a></li>
	  {/each}
  </ul>
```
Await:
```html
  <script>
	  let promise = getRandomNumber();

	  async function getRandomNumber() {
	    const res = await fetch(
	      `https://www.random.org/integers/?num=1&min=1&max=6&col=1&base=10&format=plain&rnd=new`
	    );
	    const text = await res.text();

	    if (res.ok) {
	      return text;
	    } else {
	      throw new Error(text);
	    }
	  }

	  function handleClick() {
	    promise = getRandomNumber();
	  }
  </script>

  <button on:click={handleClick}>
	  Generar úmero aleatorio
  </button>

  {#await promise}
	  <p>...esperando a random.org</p>
  {:then number}
	  <p>Número: {number}</p>
  {:catch error}
	  <p style="color: red">{error.message}</p>
  {/await}
```
Bindings:

Text input:
```html
  <script>
	  let text = "";
  </script>

  <input bind:value={text} placeholder="Texto">
  <p>{text || 'Texto'}</p>
```
Numeric inputs:
```html
  <script>
	  let a = 0;
	  let b = 0;
  </script>

  <label>
	  <input type=number bind:value={a} min=0 max=10>
	  <input type=range bind:value={a} min=0 max=10>
  </label>

  <label>
	  <input type=number bind:value={b} min=0 max=10>
	  <input type=range bind:value={b} min=0 max=10>
  </label>

  <p>{a} + {b} = {a + b}</p>
```
Checkbox:
```html
  <script>
	  let accept = false;
  </script>

  <label>
	  <input type=checkbox bind:checked={accept}>
	  He leído y acepto los términos y condiciones de uso
  </label>

  {#if accept}
	  <p/>
  {:else}
	  <p>Debes aceptar los términos y condiciones de uso para continuar</p>
  {/if}

  <button disabled={!accept}>
	  Continuar
  </button>
```
Multiple inputs with groups:
```html
  <script>
	  let size = "";
	  let flavours = [];

	  let menu = ["Hawaiana", "Napolitana"];

	  function join(flavours) {
	    if (flavours.length === 1) return flavours[0];
	    return `${flavours.slice(0, -1).join(", ")} y ${
	      flavours[flavours.length - 1]
	    }`;
	  }
  </script>

  <h2>Tamaño</h2>

  <label>
	  <input type=radio bind:group={size} value={"personal"}>
	  Personal
  </label>

  <label>
	  <input type=radio bind:group={size} value={"familiar"}>
	  Familiar
  </label>

  <h2>Sabores</h2>

  {#each menu as flavour}
	  <label>
		  <input type=checkbox bind:group={flavours} value={flavour}>
		  {flavour}
	  </label>
  {/each}

  {#if flavours.length === 0}
	  <p>Escoja uno o dos sabores</p>
  {:else}
	  <p>
		  Pidió una pizza {join(flavours)} de tamaño {size} 
	  </p>
  {/if}
```
Files:
```html
  <script>
	  let files;
  </script>

  <label for="avatar">Una imagen:</label>
  <input
	  accept="image/png, image/jpeg"
	  bind:files
	  id="avatar"
	  name="avatar"
	  type="file"
  />

  <label for="many">Varios archivos:</label>
  <input
	  bind:files
	  id="many"
	  multiple
	  type="file"
  />

  {#if files}
	  <h2>Selected files:</h2>
	  {#each Array.from(files) as file}
		  <p>{file.name} ({file.size} bytes) </p>
	  {/each}
  {/if}

```
Select
```html
  <script>
	  let questions = [
	    { id: 1, text: `¿Qué sabor quiere?` },
	    { id: 2, text: `¿Qué tamaño quiere?` }
	  ];

	  let selected;

	  let answer = "";

	  function handleSubmit() {
	    alert(`Respondió a ${selected.text} con "${answer}"`);
	  }
  </script>

  <h2>Selección de pizza</h2>

  <form on:submit|preventDefault={handleSubmit}>
	  <select bind:value={selected} on:blur="{() => answer = ''}">
		  {#each questions as question}
			  <option value={question}>
				  {question.text}
			  </option>
		  {/each}
	  </select>

	  <input bind:value={answer}>

	  <button disabled={!answer} type=submit>
		  Continuar
	  </button>
  </form>

  <p>Escogió la pregunta {selected ? selected.id : '[cargando...]'}</p>

  <style>
	  input {
	    display: block;
	    width: 500px;
	    max-width: 100%;
	  }
  </style>
```
Multiple select
```html
  <script>
	  let size = "";
	  let flavours = [];

	  let menu = ["Hawaiana", "Napolitana"];

	  function join(flavours) {
	    if (flavours.length === 1) return flavours[0];
	    return `${flavours.slice(0, -1).join(", ")} and ${
	      flavours[flavours.length - 1]
	    }`;
	  }
  </script>

  <h2>Tamaño</h2>

  <label>
	  <input type=radio bind:group={size} value={"personal"}>
	  Personal
  </label>

  <label>
	  <input type=radio bind:group={size} value={"familiar"}>
	  Familiar
  </label>

  <h2>Sabor</h2>

  <select multiple bind:value={flavours}>
	  {#each menu as flavour}
		  <option value={flavour}>
			  {flavour}
		  </option>
	  {/each}
  </select>

  {#if flavours.length === 0}
	  <p>Please select at least one flavour</p>
  {:else if flavours.length > size}
	  <p>Can't order more flavours than scoops!</p>
  {:else}
	  <p>
		  Pidió una pizza {join(flavours)} de tamaño {size}
	  </p>
  {/if}

```
Bindings con bloques Each
```html
  <script>
    let list = [];

    function add() {
      list = list.concat({ done: false, text: "" });
    }

    function clear() {
      list = list.filter(t => !t.done);
    }

    $: remaining = list.filter(t => !t.done).length;
  </script>

  <h1>Lista de tareas</h1>

  {#each list as item}
    <div>
      <input
        type=checkbox
        bind:checked={item.done}
      >

      <input
        placeholder=""
        bind:value={item.text}
        disabled={item.done}
      >
    </div>
  {/each}

  <p>{remaining} tareas pendientes</p>

  <button on:click={add}>
    Añadir tarea
  </button>

  <button on:click={clear}>
    Eliminar completadas
  </button>
```
Dimensiones:
```html
  <script>
    let w;
    let h;
    let size = 42;
    let text = "Texto";
  </script>

  <input type=range bind:value={size}>
  <input bind:value={text}>

  <p>Tamaño: {w}px x {h}px</p>

  <div bind:clientWidth={w} bind:clientHeight={h}>
    <span style="font-size: {size}px">{text}</span>
  </div>

  <style>
    input {
      display: block;
    }
    div {
      display: inline-block;
    }
  </style>
```
Bindings con componentes:
```html
  <script>
    import Keypad from "./Keypad.svelte";

    let pin;
    $: view = pin ? pin.replace(/\d(?!$)/g, "•") : "Pin";

    function handleSubmit() {
      alert(`submitted ${pin}`);
    }
  </script>

  <h1 style="color: {pin ? '#333' : '#ccc'}">{view}</h1>

  <Keypad bind:value={pin} on:submit={handleSubmit}/>
```
Así se ve Keypad.svelte:
```html
	<script>
		import { createEventDispatcher } from "svelte";

		export let value = "";

		const dispatch = createEventDispatcher();

		const select = num => () => (value += num);
		const clear = () => (value = "");
		const submit = () => dispatch("submit");
	</script>

	<div class="keypad">
		<button on:click={select(1)}>1</button>
		<button on:click={select(2)}>2</button>
		<button on:click={select(3)}>3</button>
		<button on:click={select(4)}>4</button>
		<button on:click={select(5)}>5</button>
		<button on:click={select(6)}>6</button>
		<button on:click={select(7)}>7</button>
		<button on:click={select(8)}>8</button>
		<button on:click={select(9)}>9</button>

		<button disabled={!value} on:click={clear}>clear</button>
		<button on:click={select(0)}>0</button>
		<button disabled={!value} on:click={submit}>submit</button>
	</div>

	<style>
		.keypad {
		  display: grid;
		  grid-template-columns: repeat(3, 5em);
		  grid-template-rows: repeat(4, 3em);
		  grid-gap: 0.5em;
		}

		button {
		  margin: 0;
		}
	</style>
```
