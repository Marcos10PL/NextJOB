<template>
  <div class="flex flex-col items-center justify-center space-y-6 mt-12">
    <header>
      <SharedLogo class="text-lg" />
    </header>
    <main class="max-w-[30rem]">
      <slot />
    </main>
    <LayoutFooter />
  </div>
</template>
