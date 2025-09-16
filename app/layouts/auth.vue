<template>
  <div
    class="flex flex-col items-center justify-center space-y-6 mt-12"
  >
    <SharedLogo class="text-lg" />
    <main class="max-w-[30rem]">
      <slot />
    </main>
    <LayoutFooter />
  </div>
</template>
